package com.leone.HairCutBooker.controller;


import com.leone.HairCutBooker.DTO.auth.LoginRequest;
import com.leone.HairCutBooker.DTO.auth.LoginResponse;
import com.leone.HairCutBooker.DTO.auth.RegisterRequest;
import com.leone.HairCutBooker.DTO.auth.RegisterResponse;
import com.leone.HairCutBooker.exception.UserAlreadyExistsException;
import com.leone.HairCutBooker.service.AuthenticationService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok().body(this.authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok().body(this.authService.register(request));
    }

}
