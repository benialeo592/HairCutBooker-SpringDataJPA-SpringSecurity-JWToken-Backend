package com.leone.HairCutBooker.service;

import com.leone.HairCutBooker.DTO.PrenotazioneDTO;
import com.leone.HairCutBooker.DTO.PrestazioneDTO;
import com.leone.HairCutBooker.model.Prenotazione;
import com.leone.HairCutBooker.model.Prestazione;
import com.leone.HairCutBooker.model.StatoPrenotazione;
import com.leone.HairCutBooker.repository.PrenotazioneRepo;
import com.leone.HairCutBooker.repository.PrestazioneRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PrenotazioneService {

    private PrenotazioneRepo prenotazioneRepo;
    private PrestazioneRepo prestazioneRepo;

    @Autowired
    public PrenotazioneService(PrenotazioneRepo prenotazioneRepo, PrestazioneRepo prestazioneRepo){
        this.prenotazioneRepo = prenotazioneRepo;
        this.prestazioneRepo = prestazioneRepo;
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

    public Set<PrestazioneDTO> prendiPrestazioniDaPrenotazione(Long id){
        Optional<Prenotazione> prenotazioneTrovata = this.prenotazioneRepo.findById(id);
        if (prenotazioneTrovata.isEmpty()) {
            throw new EntityNotFoundException("Prenotazione non trovata");
        }
        Set<Prestazione> prestazioni = prenotazioneTrovata.get().getPrestazioni();
        Set<PrestazioneDTO> prestazioniDTO = new HashSet<>();
        prestazioni.forEach(prestazione -> prestazioniDTO.add(new PrestazioneDTO(prestazione.getId(), prestazione.getNome(), prestazione.getPrezzo())));
        return prestazioniDTO;
    }

    public PrenotazioneDTO aggiungiPrestazioneAprenotazione(Long idPrenotazione, Long idPrestazione){
        Optional<Prenotazione> prenotazioneTrovata = prenotazioneRepo.findById(idPrenotazione);
        if (prenotazioneTrovata.isEmpty()) {
            throw new EntityNotFoundException("Prenotazione non trovata");
        }
        Optional<Prestazione> prestazioneTrovata = prenotazioneTrovata.get().getPrestazioni().stream().filter(prestazione -> prestazione.getId().equals(idPrestazione)).findFirst();
        if(!prestazioneTrovata.isEmpty()){
            throw new IllegalArgumentException("La prestazione é giá nella prenotazione");
        }
        Optional<Prestazione> prestazioneDaAggiungere = prestazioneRepo.findById(idPrestazione);
        if(prestazioneDaAggiungere.isEmpty()){
            throw new EntityNotFoundException("Prestazione non trovata");
        }
        prenotazioneTrovata.get().getPrestazioni().add(prestazioneDaAggiungere.get());
        prenotazioneRepo.save(prenotazioneTrovata.get());
        return new PrenotazioneDTO(prenotazioneTrovata.get().getId(), prenotazioneTrovata.get().getDataPrenotazione(), prenotazioneTrovata.get().getDataPrestazione(), prenotazioneTrovata.get().getStatoPrenotazione(), prenotazioneTrovata.get().getPrestazioni());
    }

    public PrenotazioneDTO cambiaStatoPrenotazione(Long id, StatoPrenotazione statoPrenotazione){
        Optional<Prenotazione> prenotazioneTrovata = prenotazioneRepo.findById(id);
        if (prenotazioneTrovata.isEmpty()) {
            throw new EntityNotFoundException("Prenotazione non trovata");
        }
        prenotazioneTrovata.get().setStatoPrenotazione(statoPrenotazione);
        prenotazioneRepo.save(prenotazioneTrovata.get());
        return new PrenotazioneDTO(prenotazioneTrovata.get().getId(), prenotazioneTrovata.get().getDataPrenotazione(), prenotazioneTrovata.get().getDataPrestazione(), prenotazioneTrovata.get().getStatoPrenotazione(), prenotazioneTrovata.get().getPrestazioni());
    }



}
