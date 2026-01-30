package com.agenda.itic.dto;


public class SalaRequest {

    private String nom_sala;
    private String ubicacio_sala;
    private String descripcio;

    public SalaRequest() {
    }

    public SalaRequest(String nom_sala, String ubicacio_sala, String descripcio) {
        this.nom_sala = nom_sala;
        this.ubicacio_sala = ubicacio_sala;
        this.descripcio = descripcio;
    }

    public String getNom_sala() {
        return nom_sala;
    }
    public void setNom_sala(String nom_sala) {
        this.nom_sala = nom_sala;
    }
    public String getUbicacio_sala() {
        return ubicacio_sala;
    }
    public void setUbicacio_sala(String ubicacio_sala) {
        this.ubicacio_sala = ubicacio_sala;
    }
    public String getDescripcio() {
        return descripcio;
    }
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }
    
}
