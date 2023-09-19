package com.leone.HairCutBooker.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Prestazione {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Double prezzo;

    @ManyToMany
    @JoinTable(
            name = "prenotazione_prestazione",
            joinColumns = @JoinColumn(name = "prestazione_id"),
            inverseJoinColumns = @JoinColumn(name = "prenotazione_id")
    )
    private Set<Prenotazione> prenotazioni;
}
