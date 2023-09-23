package com.leone.HairCutBooker.security;

import com.leone.HairCutBooker.repository.UtenteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig{

    private final UtenteRepo utenteRepo;

    @Autowired
    public ApplicationConfig(UtenteRepo utenteRepo){
        this.utenteRepo = utenteRepo;

    }
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> utenteRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("Utente non trovato"));
    }

    @Bean
    public AuthenticationProvider authProv(){
        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
        daoAuthProvider.setUserDetailsService(this.userDetailsService());
        daoAuthProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthProvider;
    }
    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
