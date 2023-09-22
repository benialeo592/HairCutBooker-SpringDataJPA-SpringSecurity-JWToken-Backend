package com.leone.HairCutBooker.service;

import com.leone.HairCutBooker.DTO.PrenotazioneDTO;
import com.leone.HairCutBooker.model.Prenotazione;
import com.leone.HairCutBooker.repository.PrenotazioneRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrenotazioneService {

    private PrenotazioneRepo prenotazioneRepo;

    @Autowired
    public PrenotazioneService(PrenotazioneRepo prenotazioneRepo){
        this.prenotazioneRepo = prenotazioneRepo;
    }

    public void cancellaPrenotazione(Long id){
        Optional<Prenotazione> prenotazioneTrovata = this.prenotazioneRepo.findById(id);
        if(prenotazioneTrovata.isEmpty()){
            throw new EntityNotFoundException("Prenotazione non trovata");
        }
        this.prenotazioneRepo.delete(prenotazioneTrovata.get());
    }

    public PrenotazioneDTO modificaPrenotazione(Prenotazione prenotazione, Long id) {
        Optional<Prenotazione> prenotazioneTrovata = this.prenotazioneRepo.findById(id);
        if (prenotazioneTrovata.isEmpty()) {
            throw new EntityNotFoundException("Prenotazione non trovata");
        }
        prenotazioneTrovata.get().setDataPrestazione(prenotazione.getDataPrestazione());
        prenotazioneTrovata.get().setDataPrenotazione(prenotazione.getDataPrenotazione());
        prenotazioneTrovata.get().setStatoPrenotazione(prenotazione.getStatoPrenotazione());
        prenotazioneTrovata.get().setUtente(prenotazione.getUtente());
        this.prenotazioneRepo.save(prenotazioneTrovata.get());
        return new PrenotazioneDTO(prenotazioneTrovata.get().getId(),prenotazioneTrovata.get().getDataPrenotazione(),prenotazioneTrovata.get().getDataPrestazione(), prenotazioneTrovata.get().getStatoPrenotazione(), prenotazioneTrovata.get().getPrestazioni());
    }



}
