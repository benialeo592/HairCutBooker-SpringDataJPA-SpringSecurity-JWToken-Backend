package com.leone.HairCutBooker.service;

import com.leone.HairCutBooker.DTO.AuthenticationRequest;
import com.leone.HairCutBooker.DTO.AuthenticationResponse;
import com.leone.HairCutBooker.DTO.RegisterRequest;
import com.leone.HairCutBooker.model.RuoloUtente;
import com.leone.HairCutBooker.model.Utente;
import com.leone.HairCutBooker.repository.UtenteRepo;
import com.leone.HairCutBooker.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class AuthenticationService {

    private UtenteRepo utenteRepo;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager authManager;

    @Autowired
    public AuthenticationService(UtenteRepo utenteRepo, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authManager){
        this.utenteRepo = utenteRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    public AuthenticationResponse register(RegisterRequest request){
        Utente utente = new Utente();
        utente.setNome(request.getNome());
        utente.setCognome(request.getCognome());
        utente.setPassword(passwordEncoder.encode(request.getPassword()));
        utente.setEmail(request.getEmail());
        utente.setRuoloUtente(RuoloUtente.UTENTE);
        utenteRepo.save(utente);
        String jwtToken = jwtService.generateToken(utente);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse login (AuthenticationRequest request){
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        Utente utente = utenteRepo.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(utente);
        return new AuthenticationResponse(jwtToken);
    }
}
