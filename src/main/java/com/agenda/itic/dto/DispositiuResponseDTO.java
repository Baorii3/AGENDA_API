package com.agenda.itic.dto;

import java.time.LocalDateTime;

public class DispositiuResponseDTO {
    private long id;
    private String nom;
    private String mac;
    private String ip;
    private String tipus;
    private boolean actiu;
    private LocalDateTime dataCreacio;
    private LocalDateTime heartbeat;

    public DispositiuResponseDTO(){}

    public DispositiuResponseDTO(long id, String nom, String mac, String ip, String tipus, boolean actiu, LocalDateTime dataCreacio, LocalDateTime heartbeat) {
        this.id = id;
        this.nom = nom;
        this.mac = mac;
        this.ip = ip;
        this.tipus = tipus;
        this.actiu = actiu;
        this.dataCreacio = dataCreacio;
        this.heartbeat = heartbeat;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public boolean isActiu() {
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