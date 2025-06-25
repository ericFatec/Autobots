package com.autobots.app.types.dtos;

import com.autobots.app.types.enums.TipoDocumento;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO utilizado para cadastro ou atualização de documentos.")
public class DocumentoDTO {

    @Schema(description = "Tipo de documento.",
            example = "CPF",
            allowableValues = {"CPF", "RG", "CNH", "PASSAPORTE"})
    private TipoDocumento tipo;

    @Schema(description = "Número do documento. Combinações de tipo + número são únicos.",
            example = "10987654321")
    private String numero;
}