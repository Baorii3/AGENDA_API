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
    private TipusSala tipus;
    private String ubicacio;
    private String descripcio;
    private Boolean activa = true;
    private LocalDateTime dataCreacio;
    private LocalDateTime dataModificacio;

    public enum TipusSala {
        agora, ateca
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

    public Sala(Long id_sala, String nom, TipusSala tipus, String ubicacio, String descripcio, Boolean activa) {
        this.id_sala = id_sala;
        this.nom = nom;
        this.tipus = tipus;
        this.ubicacio = ubicacio;
        this.descripcio = descripcio;
        this.activa = activa;
    }

    public Long getId_sala() {
        return id_sala;
    }

    public void setId_sala(Long id_sala) {
        this.id_sala = id_sala;
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