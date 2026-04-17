package com.agenda.itic.dto;

public class DispositiuRequestDTO {
    private String mac;
    private String ip;
    private String nom;
    private String tipus;
    private boolean actiu;

    public String getNom() {
        return nom;
    }

    public boolean getActiu() {
        return actiu;
    }


    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
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
}
