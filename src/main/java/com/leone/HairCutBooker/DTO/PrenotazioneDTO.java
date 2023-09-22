package com.leone.HairCutBooker.DTO;

import com.leone.HairCutBooker.model.Prestazione;
import com.leone.HairCutBooker.model.StatoPrenotazione;

import java.time.LocalDateTime;
import java.util.Set;

public class PrenotazioneDTO {

    private Long id;
    private LocalDateTime dataPrenotazione;
    private LocalDateTime dataPrestazione;

    private StatoPrenotazione statoPrenotazione;
    private Set<Prestazione> prestazioni;

    public PrenotazioneDTO(Long id, LocalDateTime dataPrenotazione, LocalDateTime dataPrestazione, StatoPrenotazione statoPrenotazione, Set<Prestazione> prestazioni) {
        this.id = id;
        this.dataPrenotazione = dataPrenotazione;
        this.dataPrestazione = dataPrestazione;
        this.statoPrenotazione = statoPrenotazione;
        this.prestazioni = prestazioni;
    }

    public PrenotazioneDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataPrenotazione() {
        return dataPrenotazione;
    }

    public void setDataPrenotazione(LocalDateTime dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    public LocalDateTime getDataPrestazione() {
        return dataPrestazione;
    }

    public void setDataPrestazione(LocalDateTime dataPrestazione) {
        this.dataPrestazione = dataPrestazione;
    }

    public StatoPrenotazione getStatoPrenotazione() {
        return this.statoPrenotazione;
    }
}
