package com.agenda.itic.dto;

import java.util.List;

public class PermisResponseDTO {

    private Long id;
    private Long idRecurs;
    private String nombreRecurs;
    private String rol;
    private int valueAccio;
    private List<String> accions;

    public PermisResponseDTO() {
    }

    public PermisResponseDTO(Long id, Long idRecurs, String nombreRecurs, String rol, int valueAccio, List<String> accions) {
        this.id = id;
        this.idRecurs = idRecurs;
        this.nombreRecurs = nombreRecurs;
        this.rol = rol;
        this.valueAccio = valueAccio;
        this.accions = accions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdRecurs() {
        return idRecurs;
    }

    public void setIdRecurs(Long idRecurs) {
        this.idRecurs = idRecurs;
    }

    public String getNombreRecurs() {
        return nombreRecurs;
    }

    public void setNombreRecurs(String nombreRecurs) {
        this.nombreRecurs = nombreRecurs;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getValueAccio() {
        return valueAccio;
    }

    public void setValueAccio(int valueAccio) {
        this.valueAccio = valueAccio;
    }

    public List<String> getAccions() {
        return accions;
    }

    public void setAccions(List<String> accions) {
        this.accions = accions;
    }
}