package com.leone.HairCutBooker.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Prenotazione {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataPrenotazione;
    private LocalDateTime dataPrestazione;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_utente")
    private Utente utente;

    @ManyToMany
    @JoinTable(
            name = "prenotazione_prestazione",
            joinColumns = @JoinColumn(name = "prenotazione_id"),
            inverseJoinColumns = @JoinColumn(name = "prestazione_id")
    )
    private Set<Prestazione> prestazioni;

    @Enumerated(EnumType.STRING)
    private StatoPrenotazione statoPrenotazione;

    public Prenotazione(Long id, LocalDateTime dataPrenotazione, LocalDateTime dataPrestazione, Utente utente, StatoPrenotazione statoPrenotazione) {
        this.id = id;
        this.dataPrenotazione = dataPrenotazione;
        this.dataPrestazione = dataPrestazione;
        this.utente = utente;
        this.statoPrenotazione = statoPrenotazione;
    }

    public Prenotazione(){}

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataPrenotazione() {
        return dataPrenotazione;
    }

    public LocalDateTime getDataPrestazione() {
        return dataPrestazione;
    }

    public Utente getUtente() {
        return utente;
    }

    public Set<Prestazione> getPrestazioni() {
        return prestazioni;
    }

    public StatoPrenotazione getStatoPrenotazione() {
        return statoPrenotazione;
    }
}
