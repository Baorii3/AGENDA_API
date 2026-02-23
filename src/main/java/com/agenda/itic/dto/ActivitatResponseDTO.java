package com.agenda.itic.dto;
import java.time.LocalDate;
import java.time.LocalTime;


public class ActivitatResponseDTO {
    
    private Long id_sala;
    private String google_id;
    private String titol;
    private String resum;
    private String descripcio;
    private LocalDate data;
    private LocalTime horaInici;
    private LocalTime horaFi;
    private String estat;
    private Boolean visible;


    
    public ActivitatResponseDTO(Long id_sala, String google_id, String titol, String resum, String descripcio,
            LocalDate data, LocalTime horaInici, LocalTime horaFi, String estat,
            Boolean visible) {
        this.id_sala = id_sala;
        this.google_id = google_id;
        this.titol = titol;
        this.resum = resum;
        this.descripcio = descripcio;
        this.data = data;
        this.horaInici = horaInici;
        this.horaFi = horaFi;
        this.estat = estat;
        this.visible = visible;
    }



    public Long getId_sala() {
        return id_sala;
    }



    public void setId_sala(Long id_sala) {
        this.id_sala = id_sala;
    }



    public String getGoogle_id() {
        return google_id;
    }



    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
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



    public String getEstat() {
        return estat;
    }



    public void setEstat(String estat) {
        this.estat = estat;
    }



    public Boolean getVisible() {
        return visible;
    }



    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    
}
