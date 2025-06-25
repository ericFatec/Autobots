package com.autobots.app.utils.validators;

import com.autobots.app.types.dtos.CredencialDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CredencialVerificador implements ConstraintValidator<ValidCredencial, CredencialDTO>{
    @Override
    public boolean isValid(CredencialDTO dto, ConstraintValidatorContext context) {
        if (dto == null || dto.getTipo() == null) {
            return false;
        }

        switch (dto.getTipo()) {
            case SENHA:
                return (dto.getLogin() != null && !dto.getLogin().isBlank()) || 
                    (dto.getSenha() != null && !dto.getSenha().isBlank());

            case CODIGO:
                return dto.getCodigo() != null && !dto.getCodigo().isBlank();

            default:
                return false;
        }
    }
}
