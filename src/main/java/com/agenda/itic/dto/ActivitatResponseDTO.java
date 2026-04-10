package com.agenda.itic.dto;
import java.time.LocalDate;
import java.time.LocalTime;


public class ActivitatResponseDTO {
    private Long id_activitat;
    private Long id_sala;
    private String nom_sala;
    private String titol;
    private String descripcio;
    private LocalDate data;
    private LocalTime horaInici;
    private LocalTime horaFi;
    private boolean activa;


    
    public ActivitatResponseDTO(Long id_activitat, Long id_sala, String nom_sala, String titol, String descripcio,
            LocalDate data, LocalTime horaInici, LocalTime horaFi, boolean activa) {
        this.id_activitat = id_activitat;
        this.id_sala = id_sala;
        this.nom_sala = nom_sala;
        this.titol = titol;
        this.descripcio = descripcio;
        this.data = data;
        this.horaInici = horaInici;
        this.horaFi = horaFi;
        this.activa = activa;
    }

    public Long getId_activitat() {
        return id_activitat;
    }


    public Long getId_sala() {
        return id_sala;
    }

    public String getNom_sala() {
        return nom_sala;
    }

    public void setNom_sala(String nom_sala) {
        this.nom_sala = nom_sala;
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



    public boolean isActiva() {
        return activa;
    }



    public void setActiva(boolean activa) {
        this.activa = activa;
    }
    
}
