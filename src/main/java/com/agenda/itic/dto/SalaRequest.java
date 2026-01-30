package com.agenda.itic.dto;

import com.agenda.itic.model.Sala.TipusSala;

public class SalaRequest {

    private String nom;
    private TipusSala tipus;
    private String ubicacio;
    private String descripcio;
    private Boolean activa;

    public SalaRequest() {
    }

    public SalaRequest(String nom, TipusSala tipus, String ubicacio, String descripcio, Boolean activa) {
        this.nom = nom;
        this.tipus = tipus;
        this.ubicacio = ubicacio;
        this.descripcio = descripcio;
        this.activa = activa;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public TipusSala getTipus() {
        return tipus;
    }

    public void setTipus(TipusSala tipus) {
        this.tipus = tipus;
    }

    public String getUbicacio() {
        return ubicacio;
    }

    public void setUbicacio(String ubicacio) {
        this.ubicacio = ubicacio;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }
}
