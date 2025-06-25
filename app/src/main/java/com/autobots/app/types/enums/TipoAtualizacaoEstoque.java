package com.autobots.app.types.enums;

public enum TipoAtualizacaoEstoque {
    ATUALIZAR,
    ADICIONAR,
    SUBTRAIR;

    public static TipoAtualizacaoEstoque fromString(String value) {
        try {
            return TipoAtualizacaoEstoque.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de operação inválida: " + value + ". Valores válidos são: ATUALIZAR, ADICIONAR, SUBTRAIR.");
        }
    }
}
