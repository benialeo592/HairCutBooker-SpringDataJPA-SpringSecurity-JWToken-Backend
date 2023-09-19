package com.leone.HairCutBooker.repository;


import com.leone.HairCutBooker.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepo extends JpaRepository<Utente,Long> {
}
