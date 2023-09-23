package com.leone.HairCutBooker.repository;


import com.leone.HairCutBooker.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepo extends JpaRepository<Utente,Long> {
    Optional<Utente> findByEmail(String email);
}
