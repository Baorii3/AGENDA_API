package com.agenda.itic.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Dispositiu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_dispositiu;
    private String ip;
    private String mac;
    private String nom;
    private String tipus;
    private boolean actiu;
    private LocalDateTime dataCreacio;
    private LocalDateTime heartbeat;

    @PrePersist
    protected void onCreate() {
        dataCreacio = LocalDateTime.now();
    }

    public Dispositiu() {
    }

    public Dispositiu(Long id_dispositiu, String nom, String ip, String mac, String tipus, boolean actiu, LocalDateTime heartbeat) {
        this.id_dispositiu = id_dispositiu;
        this.nom = nom;
        this.ip = ip;
        this.mac = mac;
        this.tipus = tipus;
        this.actiu = actiu;
        this.heartbeat = heartbeat;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
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

    public boolean getActiu() {
        return actiu;
    }

    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }

    public LocalDateTime getDataCreacio() {
        return dataCreacio;
    }

    public void setDataCreacio(LocalDateTime dataCreacio) {
        this.dataCreacio = dataCreacio;
    }

    public LocalDateTime getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(LocalDateTime heartbeat) {
        this.heartbeat = heartbeat;
    }

}
