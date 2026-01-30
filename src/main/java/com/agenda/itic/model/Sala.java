package com.agenda.itic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_sala;
    private String nom_sala;
    private String ubicacio_sala;
    private String descripcio;
    private Boolean estat = true;   

    public Sala() {
    }

    public Sala(Long id_sala, String nom_sala, String ubicacio_sala, String descripcio, Boolean estat) {
        this.id_sala = id_sala;
        this.nom_sala = nom_sala;
        this.ubicacio_sala = ubicacio_sala;
        this.descripcio = descripcio;
        this.estat = estat;
    }


    public Long getId_sala() {
        return id_sala;
    }
    public void setId_sala(Long id_sala) {
        this.id_sala = id_sala;
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
    public Boolean getEstat() {
        return estat;
    }
    public void setEstat(Boolean estat) {
        this.estat = estat;
    }

    
}