package com.leone.HairCutBooker.DTO;

import com.leone.HairCutBooker.model.Prenotazione;

import java.util.Set;

public class PrestazioneDTO {
    private Long id;
    private String nome;
    private Double prezzo;
    private Set<Prenotazione> prenotazioni;
}
