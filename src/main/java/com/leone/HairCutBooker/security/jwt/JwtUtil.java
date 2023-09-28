package com.leone.HairCutBooker.security.jwt;

import com.leone.HairCutBooker.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtUtil {

    @Value("${spring.security.jwt.secret.key}")
    private String SECRET_KEY;

    @Value("${spring.security.jwt.expiration}")
    private long expirationTime;

    public String generateToken(User user){
        return generateToken(new HashMap<>(), user);
    }

    private String generateToken(Map<String, Object> claims, User user){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date (System.currentTimeMillis() + 60 * 60 * this.expirationTime))
                .signWith(keyObjGenerator(this.SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractUsername(String token){
        return this.extractSpecificClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String email = this.extractUsername(token);
        return (email.equals((userDetails.getUsername())) && !this.isTokenExpired(token));
    }

    private <T> T extractSpecificClaim(String token, Function<Claims, T> claimFinder){
        Claims claims = this.extractAllClaims(token);
        return claimFinder.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(keyObjGenerator(this.SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    private boolean isTokenExpired(String token){
        return this.extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return this.extractSpecificClaim(token, Claims::getExpiration );
    }

    private Key keyObjGenerator(String key) {
        byte[] keyInBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyInBytes);
    }
}
