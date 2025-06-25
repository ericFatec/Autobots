package com.autobots.app.utils.validators;

import org.mapstruct.Named;

public class StringVerificador {
    @Named("mapIfValidString")
    public static String mapIfValidString(String value) {
        return isValid(value) ? value : null;
    }

	public static boolean isValid(String dado) {
        return dado != null && !dado.isBlank();
    }
}