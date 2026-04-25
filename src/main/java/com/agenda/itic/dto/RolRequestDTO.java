package com.agenda.itic.dto;

import jakarta.validation.constraints.NotBlank;

public class RolRequestDTO {

    @NotBlank(message = "El nom del rol no pot estar buit")
    private String nombre;

    public RolRequestDTO() {
    }

    public RolRequestDTO(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}