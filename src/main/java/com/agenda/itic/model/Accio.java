package com.agenda.itic.model;

public enum Accio {
    READ(1),
    CREATE(2),
    UPDATE(4),
    DELETE(8);

    private final int value;

    Accio(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
