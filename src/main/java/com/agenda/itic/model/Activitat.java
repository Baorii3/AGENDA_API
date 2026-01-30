package com.agenda.itic.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "activitats")
public class Activitat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_activitat;
    @Column(name = "id_sala")
    private Long id_sala;
    private String titol;
    private String resum;
    private String descripcio;
    private String responsable;
    @Column(name = "id_usuari")
    private Long id_usuari;
    private LocalDate data;
    private LocalTime horaInici;
    private LocalTime horaFi;
    private Integer prioritat;
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

    public Activitat(Long id_activitat, Long id_sala, String titol, String resum, String descripcio, String responsable,
            Long id_usuari, LocalDate data, LocalTime horaInici, LocalTime horaFi, Integer prioritat, Estat estat,
            Boolean visible) {
        this.id_activitat = id_activitat;
        this.id_sala = id_sala;
        this.titol = titol;
        this.resum = resum;
        this.descripcio = descripcio;
        this.responsable = responsable;
        this.id_usuari = id_usuari;
        this.data = data;
        this.horaInici = horaInici;
        this.horaFi = horaFi;
        this.prioritat = prioritat;
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

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public Long getId_usuari() {
        return id_usuari;
    }

    public void setId_usuari(Long id_usuari) {
        this.id_usuari = id_usuari;
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

    public Integer getPrioritat() {
        return prioritat;
    }

    public void setPrioritat(Integer prioritat) {
        this.prioritat = prioritat;
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
