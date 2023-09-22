package com.leone.HairCutBooker.repository;

import com.leone.HairCutBooker.model.Prestazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestazioneRepo extends JpaRepository<Prestazione, Long> {
}
