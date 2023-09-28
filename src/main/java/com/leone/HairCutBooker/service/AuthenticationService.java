package com.leone.HairCutBooker.service;

import com.leone.HairCutBooker.DTO.auth.LoginRequest;
import com.leone.HairCutBooker.DTO.auth.LoginResponse;
import com.leone.HairCutBooker.DTO.auth.RegisterRequest;
import com.leone.HairCutBooker.DTO.auth.RegisterResponse;
import com.leone.HairCutBooker.exception.UserAlreadyExistsException;
import com.leone.HairCutBooker.model.User;
import com.leone.HairCutBooker.model.enumeration.UserRole;
import com.leone.HairCutBooker.repository.UserRepo;
import com.leone.HairCutBooker.security.jwt.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public RegisterResponse register(RegisterRequest request){
        if(userRepo.findByEmail(request.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("The user already exist in the database, please use another email or contact the provider");
        }
        var user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(this.passwordEncoder.encode(request.getPassword()));
        user.setUserRole(UserRole.USER);
        userRepo.save(user);
        return new RegisterResponse("User " + user.getEmail() + " registered.");
    }

    public LoginResponse login (LoginRequest request){
        Optional<User> user = userRepo.findByEmail(request.getEmail());
        if(user.isEmpty() || !this.passwordEncoder.matches(request.getPassword(), user.get().getPassword())){
            throw new BadCredentialsException("Wrong credentials.");
        }
        String token = this.jwtUtil.generateToken(user.get());
        return new LoginResponse(token);

    }


}
