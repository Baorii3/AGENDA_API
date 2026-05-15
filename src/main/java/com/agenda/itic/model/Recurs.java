package com.agenda.itic.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Recurs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private RecursNom nombre;

    @OneToMany(mappedBy = "recurso")
    private List<Permis> permis;

    public Recurs() {
    }

    public Recurs(RecursNom nombre) {
        this.nombre = nombre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RecursNom getNombre() {
        return nombre;
    }

    public void setNombre(RecursNom nombre) {
        this.nombre = nombre;
    }

    public List<Permis> getPermis() {
        return permis;
    }

    public void setPermis(List<Permis> permis) {
        this.permis = permis;
    }
}

