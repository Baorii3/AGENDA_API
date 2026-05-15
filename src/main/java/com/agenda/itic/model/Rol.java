package com.agenda.itic.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL)
    private List<Permis> permisos;

    public Rol() {
    }

    public Rol(String nombre) {
        this.nombre = nombre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Permis> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permis> permisos) {
        this.permisos = permisos;
    }
}
