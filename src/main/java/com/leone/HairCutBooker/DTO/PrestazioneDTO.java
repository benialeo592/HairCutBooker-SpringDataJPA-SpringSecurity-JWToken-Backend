package com.leone.HairCutBooker.DTO;

import com.leone.HairCutBooker.model.Prenotazione;

import java.util.Set;

public class PrestazioneDTO {
    private Long id;
    private String nome;
    private Double prezzo;
    private Set<Prenotazione> prenotazioni;

    public PrestazioneDTO(Long id, String nome, Double prezzo, Set<Prenotazione> prenotazioni) {
        this.id = id;
        this.nome = nome;
        this.prezzo = prezzo;
        this.prenotazioni = prenotazioni;
    }

    public PrestazioneDTO(Long id, String nome, Double prezzo) {
        this.id = id;
        this.nome = nome;
        this.prezzo = prezzo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

}
