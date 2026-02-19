package com.agenda.itic.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Usuari {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuari;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false, unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    private Boolean actiu;
    private LocalDateTime dataCreacio;
    private LocalDateTime dataModificacio;

    // AUTH
    @Column(nullable = false)
    private String provider;
    @Column(nullable = false, unique = true)
    private String providerId;
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

    public Long getId() {
        return id_usuari;
    }

    public void setId(Long id) {
        this.id_usuari = id;
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
