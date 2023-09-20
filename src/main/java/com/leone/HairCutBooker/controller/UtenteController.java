package com.leone.HairCutBooker.controller;

import com.leone.HairCutBooker.DTO.PrenotazioneDTO;
import com.leone.HairCutBooker.DTO.UtenteDTO;
import com.leone.HairCutBooker.model.Prenotazione;
import com.leone.HairCutBooker.model.Utente;
import com.leone.HairCutBooker.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UtenteController {

    private UtenteService utenteService;

    @Autowired
    public UtenteController(UtenteService utenteService){
        this.utenteService = utenteService;
    }

    @PostMapping("/create")
    public UtenteDTO creaUtente(@RequestBody Utente utente){
        return this.utenteService.creaUtente(utente);
    }

    @GetMapping("/{id}")
    public UtenteDTO trovaUtente(@PathVariable Long id){
        return this.utenteService.cercaUtente(id);
    }

    @DeleteMapping("/{id}")
    public void eliminaUtente(@PathVariable Long id){
        this.utenteService.cancellaUtente(id);
    }

    @PutMapping("/update-password")
    public ResponseEntity<UtenteDTO> aggiornaUtente(@RequestBody String password, @RequestParam Long id){
        return ResponseEntity.ok().body(this.utenteService.aggiornaPasswordUtente(id, password));
    }

    @PostMapping("/{id}/booking/create")
    public ResponseEntity<List<PrenotazioneDTO>> aggiungiPrenotazioneUtente(@PathVariable Long id, @RequestBody Prenotazione prenotazione){
        return ResponseEntity.ok().body(this.utenteService.aggiungiPrenotazioneAdUtente(id, prenotazione));
    }

    @DeleteMapping("/{idUtente}/booking/delete")
    public List<Prenotazione> cancellaPrenotazioneUtente(@PathVariable Long idUtente, @RequestParam Long idPrenotazione){
        return this.utenteService.rimuoviPrenotazioneDaUtente(idUtente, idPrenotazione);
    }

}
