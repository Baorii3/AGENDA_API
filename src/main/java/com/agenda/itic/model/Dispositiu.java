package com.agenda.itic.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dispositius")
public class Dispositiu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_dispositiu;
    private String nom;
    private String ip;
    private String mac;
    private String model;
    private String sistema;
    private Boolean actiu;
    private LocalDateTime dataCreacio;
    private LocalDateTime dataModificacio;

    @PrePersist
    protected void onCreate() {
        dataCreacio = LocalDateTime.now();
        dataModificacio = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataModificacio = LocalDateTime.now();
    }

    public Dispositiu() {
    }

    public Dispositiu(Long id_dispositiu, String nom, String ip, String mac, String model, String sistema,
            Boolean actiu) {
        this.id_dispositiu = id_dispositiu;
        this.nom = nom;
        this.ip = ip;
        this.mac = mac;
        this.model = model;
        this.sistema = sistema;
        this.actiu = actiu;
    }

    public Long getId_dispositiu() {
        return id_dispositiu;
    }

    public void setId_dispositiu(Long id_dispositiu) {
        this.id_dispositiu = id_dispositiu;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public Boolean getActiu() {
        return actiu;
    }

    public void setActiu(Boolean actiu) {
        this.actiu = actiu;
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
