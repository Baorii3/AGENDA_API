package com.agenda.itic.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_sala;
    private String nom;
    @Enumerated(EnumType.STRING)
    private PisoSala ubicacio;
    private String descripcio;
    private Boolean activa = true;
    private LocalDateTime dataCreacio;
    private LocalDateTime dataModificacio;

    public enum PisoSala {
        P0, P4, P5
    }

    @PrePersist
    protected void onCreate() {
        dataCreacio = LocalDateTime.now();
        dataModificacio = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataModificacio = LocalDateTime.now();
    }

    public Sala() {
    }

    public Sala(Long id_sala, String nom, PisoSala ubicacio, String descripcio, Boolean activa) {
        this.id_sala = id_sala;
        this.nom = nom;
        this.ubicacio = ubicacio;
        this.descripcio = descripcio;
        this.activa = activa;
    }

    public Long getId() {
        return id_sala;
    }

    public void setId(Long id_sala) {
        this.id_sala = id_sala;
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

    public LocalDateTime getDataCreacio() {
        return dataCreacio;
    }

    public void setDataCreacio(LocalDateTime dataCreacio) {
        this.dataCreacio = dataCreacio;
    }

    public LocalDateTime getDataModificacio() {
        return dataModificacio;
    }

    public void setDataModificacio(LocalDateTime dataModificacio) {
        this.dataModificacio = dataModificacio;
    }
}