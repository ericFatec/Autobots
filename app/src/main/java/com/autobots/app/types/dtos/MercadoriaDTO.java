package com.autobots.app.types.dtos;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para registro e atualização de mercadoria (peças ou produtos para carros).")
public class MercadoriaDTO {

    @Schema(description = "Nome da mercadoria (peça ou produto).",
            example = "Filtro de Óleo Motor")
    private String nome;

    @Schema(description = "Data de validade da mercadoria no formato ISO 8601, se aplicável.",
            example = "2027-05-31T23:59:59Z")
    private Instant dataValidade;

    @Schema(description = "Data de fabricação da mercadoria no formato ISO 8601.",
            example = "2025-04-15T08:00:00Z")
    private Instant dataFabricacao;

    @Schema(description = "Valor unitário da mercadoria em reais.",
            example = "120.50")
    private Double valor;

    @Schema(description = "Descrição detalhada da mercadoria.",
            example = "Filtro de óleo para motores 1.0 a 2.0, alta eficiência na filtragem.")
    private String descricao;

    @Schema(description = "Quantidade disponível em estoque.",
            example = "75")
    private Integer quantidade;
}