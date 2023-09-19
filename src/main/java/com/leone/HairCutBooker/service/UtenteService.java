package com.leone.HairCutBooker.service;


import com.leone.HairCutBooker.DTO.UtenteDTO;
import com.leone.HairCutBooker.model.Prenotazione;
import com.leone.HairCutBooker.model.Utente;
import com.leone.HairCutBooker.repository.UtenteRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

    private UtenteRepo utenteRepo;

    @Autowired
    public UtenteService(UtenteRepo utenteRepo){
        this.utenteRepo = utenteRepo;
    }

    public UtenteDTO cercaUtente(Long id){

        Utente utente = this.utenteRepo.findById(id).get();
        return new UtenteDTO(utente.getId(), utente.getNome(), utente.getCognome(), utente.getEmail());
    }

    public UtenteDTO creaUtente(Utente utente){
        Utente utenteSalvato = this.utenteRepo.save(utente);
        return new UtenteDTO(utenteSalvato.getId(), utenteSalvato.getNome(), utenteSalvato.getCognome(), utenteSalvato.getEmail());
    }

    public void cancellaUtente(Long id){
        utenteRepo.deleteById(id);
    }

    public UtenteDTO aggiornaPasswordUtente(Long id, String password){
        Optional<Utente> utenteDaAggiornare = this.utenteRepo.findById(id);
        if(utenteDaAggiornare.isEmpty()){
            throw new EntityNotFoundException("Utente non trovato");
        }
            utenteDaAggiornare.get().setPassword(password);
            Utente utenteAggiornato = this.utenteRepo.save(utenteDaAggiornare.get());
            return new UtenteDTO(utenteAggiornato.getId(), utenteAggiornato.getNome(), utenteAggiornato.getCognome(), utenteAggiornato.getEmail());
    }

    public List<Prenotazione>  getPrenotazioniUtente(Long idUtente) throws EntityNotFoundException{
        Optional<Utente> utente = this.utenteRepo.findById(idUtente);
        if(utente.isEmpty()){
            throw new EntityNotFoundException("Utente non trovato, lista inesistente");
        }
        return utente.get().getPrenotazioni();
    }

    public List<Prenotazione> aggiungiPrenotazioneAdUtente(Long idUtente, Prenotazione prenotazione){
        Optional<Utente> utente = this.utenteRepo.findById(idUtente);
        if(utente.isEmpty()){
            throw new EntityNotFoundException("Utente non trovato, lista inesistente");
        }
        utente.get().getPrenotazioni().add(prenotazione);
        return utente.get().getPrenotazioni();
    }

    public List<Prenotazione> rimuoviPrenotazioneDaUtente(Long idUtente, Long idPrenotazione){
        Optional<Utente> utente = this.utenteRepo.findById(idUtente);
        if(utente.isEmpty()){
            throw new EntityNotFoundException("Utente non trovato, lista inesistente");
        }
        Optional<Prenotazione> prenotazioneTrovata = utente.get().getPrenotazioni().stream().filter( prenotazione -> prenotazione.getId().equals(idPrenotazione)).findFirst();
        if(prenotazioneTrovata.isEmpty()){
            throw new EntityNotFoundException("Prenotazione non trovata");
        }
        utente.get().getPrenotazioni().remove(prenotazioneTrovata.get());
        this.utenteRepo.save(utente.get());
        return utente.get().getPrenotazioni();
    }
}
