package com.agenda.itic.dto;
import java.time.LocalDate;
import java.time.LocalTime;


public class ActivitatResponseDTO {
    
    private Long id_sala;
    private String titol;
    private String resum;
    private String descripcio;
    private Long id_responsable;
    private LocalDate data;
    private LocalTime horaInici;
    private LocalTime horaFi;
    private Integer prioritat;
    private String estat;
    private Boolean visible;


    
    public ActivitatResponseDTO(Long id_sala, String titol, String resum, String descripcio, Long id_responsable,
            LocalDate data, LocalTime horaInici, LocalTime horaFi, Integer prioritat, String estat,
            Boolean visible) {
        this.id_sala = id_sala;
        this.titol = titol;
        this.resum = resum;
        this.descripcio = descripcio;
        this.id_responsable = id_responsable;
        this.data = data;
        this.horaInici = horaInici;
        this.horaFi = horaFi;
        this.prioritat = prioritat;
        this.estat = estat;
        this.visible = visible;
    }
    
}
