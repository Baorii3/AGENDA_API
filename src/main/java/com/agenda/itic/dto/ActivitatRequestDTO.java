package com.agenda.itic.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ActivitatRequestDTO {

    @NotNull(message = "El id de la sala no pot ser null")
    private Long id_sala;
    @NotBlank(message = "El títol de l'activitat no pot ser buit")
    private String titol;
    @NotBlank(message = "La descripció de l'activitat no pot ser buida")
    private String descripcio;
    @NotNull(message = "La data de l'activitat no pot ser null")
    private LocalDate data;
    @NotNull(message = "La hora de inicio de la actividad no puede ser null")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaInici;
    @NotNull(message = "La hora de fin de la actividad no puede ser null")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaFi;

        public ActivitatRequestDTO(Long id_sala, String titol, String descripcio,
            LocalDate data, LocalTime horaInici, LocalTime horaFi) {
        this.id_sala = id_sala;
        this.titol = titol;
        this.descripcio = descripcio;
        this.data = data;
        this.horaInici = horaInici;
        this.horaFi = horaFi;
    }

    public Long getId_sala() {
        return id_sala;
    }

    public void setId_sala(Long id_sala) {
        this.id_sala = id_sala;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHoraInici() {
        return horaInici;
    }

    public void setHoraInici(LocalTime horaInici) {
        this.horaInici = horaInici;
    }

    public LocalTime getHoraFi() {
        return horaFi;
    }

    public void setHoraFi(LocalTime horaFi) {
        this.horaFi = horaFi;
    }

}
