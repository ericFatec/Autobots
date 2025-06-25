package com.autobots.app.types.dtos;

import com.autobots.app.types.enums.TipoAtualizacaoEstoque;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para atualização do estoque da mercadoria.")
public class MercadoriaEstoqueDTO {
    @Schema(description = "Tipo da operação de atualização do estoque.",
            example = "ADICIONAR", required = true,
            allowableValues = {"ATUALIZAR", "ADICIONAR", "SUBTRAIR"})
    private TipoAtualizacaoEstoque operacao;

    @Schema(description = "Quantidade para a operação de estoque.",
            example = "10")
    private Integer quantidade;
}