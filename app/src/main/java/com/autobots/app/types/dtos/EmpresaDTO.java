package com.autobots.app.types.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para atualização das informações básicas de uma empresa.")
public class EmpresaDTO {

    @Schema(description = "Razão social da empresa (nome legal registrado). Valor único!",
            example = "Inovatech Soluções Digitais S.A.")
    private String razaoSocial;

    @Schema(description = "Nome fantasia da empresa.",
            example = "Inovatech")
    private String nomeFantasia;
}
