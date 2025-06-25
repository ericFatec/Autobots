package com.autobots.app.types.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para cadastro ou atualização do telefone de contato de usuários ou empresas.")
public class TelefoneDTO {

    @Schema(description = "Código DDD do telefone.",
            example = "11")
    private String ddd;

    @Schema(description = "Número do telefone, combinações de DDD + Número são únicos.",
            example = "123456789")
    private String numero;
}