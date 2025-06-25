package com.autobots.app.types.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para cadastro e atualização de endereço.")
public class EnderecoDTO {

    @Schema(description = "Estado (UF).",
            example = "SP")
    private String estado;

    @Schema(description = "Cidade.",
            example = "São Paulo")
    private String cidade;

    @Schema(description = "Bairro.",
            example = "Vila Mariana")
    private String bairro;

    @Schema(description = "Rua ou avenida.",
            example = "Rua Vergueiro")
    private String rua;

    @Schema(description = "Número do endereço.",
            example = "1234")
    private String numero;

    @Schema(description = "Código postal (CEP).",
            example = "04101-000")
    private String codigoPostal;

    @Schema(description = "Informações adicionais sobre o endereço.",
            example = "Apartamento 12, bloco B")
    private String informacoesAdicionais;
}