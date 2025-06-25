package com.autobots.app.types.enums;

public enum TipoVeiculo {
    HATCH,
    SEDA,
    SUV,
    PICKUP,
    SW;

    public static TipoVeiculo fromString(String value) {
        try {
            return TipoVeiculo.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de veículo inválido: " + value + ". Valores válidos são: HATCH, SEDA, SUV, PICKUP, SW.");
        }
    }
}
