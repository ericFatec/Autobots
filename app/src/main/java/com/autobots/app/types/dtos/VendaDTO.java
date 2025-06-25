package com.autobots.app.types.dtos;

import java.util.Map;
import java.util.Set;

import com.autobots.app.utils.validators.ValidVenda;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@ValidVenda
@Schema(description = "DTO para cadastro de vendas.")
public class VendaDTO {

    @Schema(description = "Identificação única da venda.",
            example = "Venda-2025-06-16-001",
            required = true)
    @NotBlank
    private String identificacao;

    @Schema(description = "ID do cliente.",
            example = "1",
            required = true)
    @NotNull
    private Long clienteId;

    @Schema(description = "ID do vendedor.",
            example = "2",
            required = true)
    @NotNull
    private Long vendedorId;

    @Schema(description = "Mapa de IDs das mercadorias com suas respectivas quantidades. (As mercadorias devem pertencer ao vendedor ou à empresa do vendedor)",
            example = "{\"1\":3}")
    private Map<Long, Integer> mercadorias;

    @Schema(description = "Conjunto de IDs dos serviços. (Serviços só podem ser prestados por usuários pertencentes à uma empresa)",
            example = "[1]")
    private Set<Long> servicos;

    @Schema(description = "ID do veículo associado à venda. (Veículo deve pertencer ao cliente)",
            example = "1")
    private Long veiculoId;
}
