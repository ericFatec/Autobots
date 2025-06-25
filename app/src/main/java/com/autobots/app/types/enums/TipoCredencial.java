package com.autobots.app.types.enums;

public enum TipoCredencial {
    SENHA,
    CODIGO;

    public static TipoCredencial fromString(String value) {
        try {
            return TipoCredencial.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de credencial inválida: " + value + ". Valores válidos são: SENHA, CODIGO.");
        }
    }
}
