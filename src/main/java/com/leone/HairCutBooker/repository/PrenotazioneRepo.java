package com.leone.HairCutBooker.repository;

import com.leone.HairCutBooker.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrenotazioneRepo extends JpaRepository<Prenotazione, Long> {
}
