package com.leone.HairCutBooker.controller;

import com.leone.HairCutBooker.DTO.AuthenticationRequest;
import com.leone.HairCutBooker.DTO.AuthenticationResponse;
import com.leone.HairCutBooker.DTO.RegisterRequest;
import com.leone.HairCutBooker.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AutenticazioneController {

    private AuthenticationService authService;

    @Autowired
    public AutenticazioneController(AuthenticationService authService){
        this.authService = authService;
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(this.authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(this.authService.login(request));
    }
}
