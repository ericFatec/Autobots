package com.autobots.app.utils.validators;

import com.autobots.app.types.dtos.VendaDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VendaVerificador implements ConstraintValidator<ValidVenda, VendaDTO>{
    @Override
    public boolean isValid(VendaDTO dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return false;
        }

        boolean mercadoriasEmpty = (dto.getMercadorias() == null || dto.getMercadorias().isEmpty());
        boolean servicosEmpty = (dto.getServicos() == null || dto.getServicos().isEmpty());

        if (mercadoriasEmpty && servicosEmpty) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("É necessário informar mercadorias ou serviços.")
                .addConstraintViolation();
            return false;
        }

        if (!servicosEmpty && dto.getVeiculoId() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Veículo deve ser informado se houver serviços.")
                .addPropertyNode("veiculoId")
                .addConstraintViolation();
            return false;
        }

        return true;
    }
}
