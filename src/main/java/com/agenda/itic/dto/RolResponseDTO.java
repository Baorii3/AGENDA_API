package com.agenda.itic.dto;

import java.util.List;

public class RolResponseDTO {

    private Long id;
    private String nombre;
    private List<Long> permisosIds;

    public RolResponseDTO() {
    }

    public RolResponseDTO(Long id, String nombre, List<Long> permisosIds) {
        this.id = id;
        this.nombre = nombre;
        this.permisosIds = permisosIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Long> getPermisosIds() {
        return permisosIds;
    }

    public void setPermisosIds(List<Long> permisosIds) {
        this.permisosIds = permisosIds;
    }
}