package com.autobots.app.types.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para cadastro e atualização de serviços.")
public class ServicoDTO {

    @Schema(description = "Nome do serviço.",
            example = "Troca de óleo")
    private String nome;

    @Schema(description = "Valor do serviço em reais.",
            example = "150.50")
    private Double valor;

    @Schema(description = "Descrição detalhada do serviço.",
            example = "Troca completa do óleo do motor com óleo sintético 5W30")
    private String descricao;
}