package com.agenda.itic.dto;

import jakarta.validation.constraints.NotBlank;

public class RecursRequestDTO {

    @NotBlank(message = "El nom del recurs no pot estar buit")
    private String nombre;

    public RecursRequestDTO() {
    }

    public RecursRequestDTO(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}