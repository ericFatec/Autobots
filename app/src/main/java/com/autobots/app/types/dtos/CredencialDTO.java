package com.autobots.app.types.dtos;

import com.autobots.app.types.enums.TipoCredencial;
import com.autobots.app.utils.validators.ValidCredencial;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@ValidCredencial
@Data
@Schema(
    description = "DTO utilizado para cadastro, atualização ou autenticação via credenciais, podendo ser login/senha ou código de barras."
)
public class CredencialDTO {

    @Schema(description = "Tipo de credencial utilizada para login: SENHA (login e senha) ou CODIGO (código de barras).", 
            example = "SENHA", required = true,
            allowableValues = {"SENHA", "CODIGO"})
    private TipoCredencial tipo;

    @Schema(description = "Valor único de login do usuário.", 
            example = "usuario123")
    private String login;

    @Schema(description = "Senha do usuário.", 
            example = "senha123")
    private String senha;

    @Schema(description = "Código de barras utilizado para autenticação.", 
            example = "12345")
    private String codigo;
}
