package com.leone.HairCutBooker.controller;

import com.leone.HairCutBooker.DTO.PrenotazioneDTO;
import com.leone.HairCutBooker.DTO.PrestazioneDTO;
import com.leone.HairCutBooker.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/bookings")
public class PrenotazioneController {

    private PrenotazioneService prenotazioneService;

    @Autowired
    public PrenotazioneController(PrenotazioneService prenotazioneService){
        this.prenotazioneService = prenotazioneService;
    }

    @PostMapping("/{idPrenotazione}/service-provision")
    public ResponseEntity<PrenotazioneDTO> aggiungiPrestazioniAllaPrenotazione(@PathVariable Long idPrenotazione, @RequestParam Long idPrestazione){
        return ResponseEntity.ok().body(prenotazioneService.aggiungiPrestazioneAprenotazione(idPrenotazione, idPrestazione));
    }

    @GetMapping("/{id}/service-provisions")
    public ResponseEntity<Set<PrestazioneDTO>> prendiPrestazioniPrenotazione(@PathVariable Long id){
        return ResponseEntity.ok().body(prenotazioneService.prendiPrestazioniDaPrenotazione(id));
    }

    @DeleteMapping("/{id}")
    public void cancellaPrenotazione(@PathVariable Long id){
        this.prenotazioneService.cancellaPrenotazione(id);
    }

}
