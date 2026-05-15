package com.agenda.itic.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PermisRequestDTO {

    @NotNull(message = "El id del recurs no pot ser null")
    private Long idRecurs;

    @NotNull(message = "El id del rol no pot ser null")
    private Long idRol;

    @Min(value = 0, message = "El valor mínim dels permisos és 0")
    @Max(value = 15, message = "El valor màxim dels permisos és 15")
    private int valueAccio;

    public PermisRequestDTO() {
    }

    public PermisRequestDTO(Long idRecurs, Long idRol, int valueAccio) {
        this.idRecurs = idRecurs;
        this.idRol = idRol;
        this.valueAccio = valueAccio;
    }

    public Long getIdRecurs() {
        return idRecurs;
    }

    public void setIdRecurs(Long idRecurs) {
        this.idRecurs = idRecurs;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public int getValueAccio() {
        return valueAccio;
    }

    public void setValueAccio(int valueAccio) {
        this.valueAccio = valueAccio;
    }
}