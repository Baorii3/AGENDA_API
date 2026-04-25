package com.agenda.itic.dto;

public class PermisUsuariDto {

    private String recurso;
    private int valor;

    public PermisUsuariDto() {
    }

    public PermisUsuariDto(String recurso, int valor) {
        this.recurso = recurso;
        this.valor = valor;
    }

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}