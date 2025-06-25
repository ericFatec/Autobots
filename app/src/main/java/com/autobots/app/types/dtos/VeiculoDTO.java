package com.autobots.app.types.dtos;

import com.autobots.app.types.enums.TipoVeiculo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para cadastro ou atualização de veículos.")
public class VeiculoDTO {

    @Schema(description = "Tipo do veículo.",
            example = "SUV",
            allowableValues = {"HATCH", "SEDA", "SUV", "PICKUP", "SW"})
    private TipoVeiculo tipo;

    @Schema(description = "Modelo do veículo.",
            example = "Honda CR-V 2023")
    private String modelo;

    @Schema(description = "Placa do veículo, no formato padrão brasileiro. Valor único!",
            example = "ABC1D23")
    private String placa;
}