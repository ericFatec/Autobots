package com.autobots.app.types.dtos;

import java.time.Instant;
import java.util.Set;

import com.autobots.app.types.enums.TipoPerfil;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "DTO para atualização parcial dos dados do usuário.")
public class UsuarioAtualizarDTO {

    @Schema(description = "Nome completo do usuário.",
            example = "Ana Paula Souza")
    private String nome;

    @Schema(description = "Nome social do usuário.",
            example = "Aninha")
    private String nomeSocial;

    @Schema(description = "Conjunto de emails válidos do usuário.",
            example = "[\"ana.souza@email.com\", \"a.p.souza@gmail.com\"]")
    private Set<@NotBlank String> email;

    @Schema(description = "Data de nascimento do usuário em formato ISO 8601.",
            example = "1988-03-25T00:00:00Z")
    private Instant dataNascimento;

    @Schema(description = "Perfis atribuídos ao usuário.",
            example = "[\"FUNCIONARIO\"]",
            allowableValues = {"CLIENTE", "FUNCIONARIO", "FORNECEDOR"})
    private Set<@NotNull TipoPerfil> perfis;
}
