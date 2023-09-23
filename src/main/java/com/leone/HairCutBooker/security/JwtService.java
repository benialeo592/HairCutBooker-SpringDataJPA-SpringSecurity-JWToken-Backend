package com.leone.HairCutBooker.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.catalina.UserDatabase;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "IYqHzQmggreN20JYzNhBXMalXbq/IxkxxEap9eHcaCRWmuK3MUZXYQ6zBKjS8CIeZQCFQ/nigOJaAd+saT22GB9p+sDbZn0tmQkvyf43cXEgTAwZtQwT+e914FmyOUCjMVqy9hH/5cCIlkz83HA3kckGqBlPH0PsdltsCOpWDvgEVv1Y1z8NdlidkgM82ucemnPJJjgKxlJIm4yEICraEz5mLvCS8OEsLELx2jAivp6Al/+faqamYMBMz/1LLlemxouSszWvqDCQI/l8mF+t2FNwYyQV+lMBnd+W9FwIVRlaVioPL8d5bzVCva9DBg35Hue33nIc0LZ8oXQRbGoEng+7lxRj0WR95CIubeepwxc=\n";
    public String extractEmail(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
         return Jwts
                 .builder()
                 .setClaims(extraClaims)
                 .setSubject(userDetails.getUsername())
                 .setIssuedAt(new Date(System.currentTimeMillis()))
                 .setExpiration(new Date(System.currentTimeMillis()* 24))
                 .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                 .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String email = this.extractEmail(token);
        return (email.equals((userDetails.getUsername())) && !this.isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return this.extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return this.extractClaim(token, Claims::getExpiration);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(this.getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
