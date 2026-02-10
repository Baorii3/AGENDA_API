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
    @ManyToOne
    @JoinColumn(name = "id_sala", referencedColumnName = "id_sala")
    private Sala sala;
    private String titol;
    private String resum;
    private String descripcio;
    @ManyToOne
    @JoinColumn(name = "id_usuari", referencedColumnName = "id_usuari")
    private Usuari user;
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

    public Activitat(Long id_activitat, Sala sala, String titol, String resum, String descripcio,
            Usuari user, LocalDate data, LocalTime horaInici, LocalTime horaFi, Integer prioritat, Estat estat,
            Boolean visible) {
        this.id_activitat = id_activitat;
        this.sala = sala;
        this.titol = titol;
        this.resum = resum;
        this.descripcio = descripcio;
        this.user = user;
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

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
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

    public Usuari getUser() {
        return user;
    }

    public void setUser(Usuari user) {
        this.user = user;
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
