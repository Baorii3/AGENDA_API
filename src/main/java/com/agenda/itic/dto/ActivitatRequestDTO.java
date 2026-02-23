package com.agenda.itic.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.agenda.itic.model.Activitat.Estat;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ActivitatRequestDTO {

    private Long id_sala;
    private String titol;
    private String resum;
    private String descripcio;
    private LocalDate data;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaInici;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaFi;
    private Estat estat;
    private Boolean visible;

        public ActivitatRequestDTO(Long id_sala, String titol, String resum, String descripcio,
            LocalDate data, LocalTime horaInici, LocalTime horaFi, String estat,
            Boolean visible) {
        this.id_sala = id_sala;
        this.titol = titol;
        this.resum = resum;
        this.descripcio = descripcio;
        this.data = data;
        this.horaInici = horaInici;
        this.horaFi = horaFi;
        this.estat = Estat.valueOf(estat);
        this.visible = visible;
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

    public String getResum() {
        return resum;
    }

    public void setResum(String resum) {
        this.resum = resum;
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

    public Estat getEstat() {
        return estat;
    }

    public void setEstat(Estat estat) {
        this.estat = estat;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

}
