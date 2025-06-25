package com.autobots.app.types.enums;

public enum TipoDocumento {
    CPF,
    RG,
    CNH,
    PASSAPORTE;

    public static TipoDocumento fromString(String value) {
        try {
            return TipoDocumento.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de documento inválido: " + value + ". Valores válidos são: CPF, RG, CNH, PASSAPORTE.");
        }
    }
}
