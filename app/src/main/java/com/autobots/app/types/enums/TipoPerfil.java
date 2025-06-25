package com.autobots.app.types.enums;

public enum TipoPerfil {
    CLIENTE,
    FUNCIONARIO,
    FORNECEDOR;

    public static TipoPerfil fromString(String value) {
        try {
            return TipoPerfil.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de perfil inválido: " + value + ". Valores válidos são: CLIENTE, FUNCIONARIO, FORNECEDOR.");
        }
    }
}
