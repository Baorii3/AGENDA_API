package com.agenda.itic.dto;

import com.agenda.itic.model.Sala.PisoSala;

public class SalaRequest {

    private String nom;
    private PisoSala ubicacio;
    private String descripcio;
    private Boolean activa;

    public SalaRequest() {
    }

    public SalaRequest(String nom, PisoSala ubicacio, String descripcio, Boolean activa) {
        this.nom = nom;
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

    public PisoSala getUbicacio() {
        return ubicacio;
    }

    public void setUbicacio(PisoSala ubicacio) {
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
