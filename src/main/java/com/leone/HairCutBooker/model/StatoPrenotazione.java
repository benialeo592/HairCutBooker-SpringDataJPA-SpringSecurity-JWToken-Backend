package com.leone.HairCutBooker.model;

public enum StatoPrenotazione {
    IN_ATTESA("In attesa di conferma"),
    CONFERMATA("Confermata dal negozio"),
    ANNULLATA("Annullata dala negozio"),
    COMPLETATA("Completata");

    private String descrizione;

    public String getDescrizione(){
        return descrizione;
    }
    StatoPrenotazione(String descrizione){
        this.descrizione = descrizione;
    }
}
