package com.agenda.itic.dto;

import com.agenda.itic.model.Usuari;

public class UsuariRequestDTO {
    private String nom;
    private String email;
    private Usuari.Rol rol;
    private Boolean actiu;

    public UsuariRequestDTO() {
    }

    public UsuariRequestDTO(String nom, String email, Usuari.Rol rol, Boolean actiu) {
        this.nom = nom;
        this.email = email;
        this.rol = rol;
        this.actiu = actiu;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Usuari.Rol getRol() {
        return rol;
    }

    public void setRol(Usuari.Rol rol) {
        this.rol = rol;
    }

    public Boolean getActiu() {
        return actiu;
    }

    public void setActiu(Boolean actiu) {
        this.actiu = actiu;
    }
}
