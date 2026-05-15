package com.agenda.itic.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Permis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "recurso_id")
    private Recurs recurso;
    
    private int valueAccio;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    public Permis() {}

    public int getValueAccio() { return valueAccio; }

    public void setAcciones(int acciones) {
        if (acciones < 0 || acciones > 15) {
            throw new IllegalArgumentException("Permisos inválidos: debe ser entre 0 y 15");
        }
        this.valueAccio = acciones;
    }

    public boolean tieneAccion(Accio a) {
        return (this.valueAccio & a.getValue()) != 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Recurs getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurs recurso) {
        this.recurso = recurso;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    
}
