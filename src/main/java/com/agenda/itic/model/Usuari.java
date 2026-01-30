package com.agenda.itic.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuaris")
public class Usuari {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuari;
    private String nom;
    private String email;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    private Boolean actiu;
    private LocalDateTime dataCreacio;
    private LocalDateTime dataModificacio;

    public enum Rol {
        admin, usuari
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

    public Usuari() {
    }

    public Usuari(Long id_usuari, String nom, String email, Rol rol, Boolean actiu) {
        this.id_usuari = id_usuari;
        this.nom = nom;
        this.email = email;
        this.rol = rol;
        this.actiu = actiu;
    }

    public Long getId_usuari() {
        return id_usuari;
    }

    public void setId_usuari(Long id_usuari) {
        this.id_usuari = id_usuari;
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

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
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
