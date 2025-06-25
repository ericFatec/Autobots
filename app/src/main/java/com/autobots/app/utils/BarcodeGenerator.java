package com.autobots.app.utils;

import java.security.SecureRandom;

import com.autobots.app.types.enums.TipoPerfil;

public class BarcodeGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int DEFAULT_LENGTH = 10;
    private static final String DIGITS = "0123456789";

    public static String generateCodigo(TipoPerfil perfil) {
        return generateCodigo(perfil, DEFAULT_LENGTH);
    }

    public static String generateCodigo(TipoPerfil perfil, int length) {
        String prefix = switch (perfil) {
            case ADMIN -> "ADM";
            case GERENTE -> "GER";
            case VENDEDOR -> "VEN";
            case CLIENTE -> "CLI";
        };

        StringBuilder codigo = new StringBuilder(prefix);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(DIGITS.length());
            codigo.append(DIGITS.charAt(index));
        }
        return codigo.toString();
    }
}
