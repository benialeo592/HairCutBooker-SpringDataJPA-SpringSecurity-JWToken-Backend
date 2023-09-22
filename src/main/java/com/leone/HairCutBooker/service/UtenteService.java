package com.leone.HairCutBooker.service;


import com.leone.HairCutBooker.DTO.PrenotazioneDTO;
import com.leone.HairCutBooker.DTO.UtenteDTO;
import com.leone.HairCutBooker.model.Prenotazione;
import com.leone.HairCutBooker.model.Utente;
import com.leone.HairCutBooker.repository.PrenotazioneRepo;
import com.leone.HairCutBooker.repository.UtenteRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

    private UtenteRepo utenteRepo;
    private PrenotazioneRepo prenotazioneRepo;

    @Autowired
    public UtenteService(UtenteRepo utenteRepo, PrenotazioneRepo prenotazioneRepo){
        this.utenteRepo = utenteRepo; this.prenotazioneRepo = prenotazioneRepo;
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

    public List<PrenotazioneDTO> getPrenotazioniUtente(Long idUtente) throws EntityNotFoundException{
        Optional<Utente> utente = this.utenteRepo.findById(idUtente);
        if(utente.isEmpty()){
            throw new EntityNotFoundException("Utente non trovato, lista inesistente");
        }
        List<PrenotazioneDTO> prenotazioni = new ArrayList<>();
        utente.get().getPrenotazioni().forEach(element -> prenotazioni.add(new PrenotazioneDTO(element.getId(),element.getDataPrenotazione(), element.getDataPrestazione(), element.getStatoPrenotazione(), element.getPrestazioni())));
        return prenotazioni;
    }

    public List<PrenotazioneDTO> aggiungiPrenotazioneAdUtente(Long idUtente, Prenotazione prenotazione) throws EntityNotFoundException{
        Optional<Utente> utente = this.utenteRepo.findById(idUtente);
        if(utente.isEmpty()){
            throw new EntityNotFoundException("Utente non trovato, lista inesistente");
        }
        prenotazione.setUtente(utente.get());
        prenotazioneRepo.save(prenotazione);
        List<Prenotazione> listaPrenotazioniUtente = utente.get().getPrenotazioni();
        listaPrenotazioniUtente.add(prenotazione);
        List<PrenotazioneDTO> listaDaRitornare = new ArrayList<>();
        listaPrenotazioniUtente.forEach(element -> {
                    listaDaRitornare.add(new PrenotazioneDTO(element.getId(),
                                    element.getDataPrenotazione(),
                                    element.getDataPrestazione(),
                                    element.getStatoPrenotazione(),
                                    element.getPrestazioni()));
                            });
        return listaDaRitornare;
    }

    public void rimuoviPrenotazioneDaUtente(Long idUtente, Long idPrenotazione) throws EntityNotFoundException{
        Optional<Utente> utente = this.utenteRepo.findById(idUtente);
        if(utente.isEmpty()){
            throw new EntityNotFoundException("Utente non trovato, lista inesistente");
        }
        Optional<Prenotazione> prenotazioneTrovata = utente.get().getPrenotazioni()
                                                                    .stream()
                                                                    .filter( prenotazione ->
                                                                            prenotazione.getId().equals(idPrenotazione)).findFirst();
        if(prenotazioneTrovata.isEmpty()){
            throw new EntityNotFoundException("Prenotazione non trovata");
        }
        this.prenotazioneRepo.delete(prenotazioneTrovata.get());

    }
}
