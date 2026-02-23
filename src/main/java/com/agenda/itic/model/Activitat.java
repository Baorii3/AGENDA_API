package com.agenda.itic.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class Activitat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_activitat;
    private Long id_sala;
    private String google_id;
    private String titol;
    private String resum;
    private String descripcio;
    private LocalDate data;
    private LocalTime horaInici;
    private LocalTime horaFi;
    private Estat estat;
    private Boolean visible;
    private LocalDateTime dataCreacio;
    private LocalDateTime dataModificacio;

    public enum Estat {
        programada, cancelada
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

    public Activitat() {
    }

        public Activitat(Long id_activitat, String google_id, Long id_sala, String titol, String resum, String descripcio,
            Usuari user, LocalDate data, LocalTime horaInici, LocalTime horaFi, Estat estat,
            Boolean visible) {
        this.id_activitat = id_activitat;
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

    public Long getId_activitat() {
        return id_activitat;
    }

    public void setId_activitat(Long id_activitat) {
        this.id_activitat = id_activitat;
    }

    public Long getId_sala() {
        return id_sala;
    }

    public void setId_sala(Long id_sala) {
        this.id_sala = id_sala;
    }

    public String getGoogleId() {
        return google_id;
    }

    public void setGoogleId(String google_id) {
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
