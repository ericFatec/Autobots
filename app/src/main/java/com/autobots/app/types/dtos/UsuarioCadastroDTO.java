package com.autobots.app.types.dtos;

import java.time.Instant;
import java.util.Set;

import com.autobots.app.types.enums.TipoPerfil;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
@Schema(description = "DTO para cadastro de um novo usuário com informações pessoais, documentos e contatos.")
public class UsuarioCadastroDTO {

    @NotBlank
    @Schema(description = "Nome completo do usuário.",
            example = "Lucas Oliveira", required = true)
    private String nome;

    @Schema(description = "Nome social do usuário.",
            example = "Luka")
    private String nomeSocial;

    @Schema(description = "Conjunto de emails válidos do usuário.",
            example = "[\"lucas.oliveira@email.com\", \"l.oliveira@gmail.com\"]")
    private Set<@NotBlank String> email;

    @Schema(description = "Login para autenticação do usuário.",
            example = "lucas.oliveira")
    private String login;

    @NotBlank
    @Schema(description = "Senha do usuário para autenticação.",
            example = "senhaForte123", required = true)
    private String senha;

    @NotNull
    @Schema(description = "Data de nascimento do usuário em formato ISO 8601.",
            example = "1992-07-15T00:00:00Z", required = true)
    private Instant dataNascimento;

    @NotEmpty
    @Schema(description = "Perfis atribuídos ao usuário.",
            example = "[\"ADMIN\", \"VENDEDOR\"]",
            allowableValues = {"ADMIN", "GERENTE", "VENDEDOR", "CLIENTE"})
    private Set<@NotNull TipoPerfil> perfis;

    @NotEmpty
    @Schema(description = "Documentos do usuário, pelo menos um é obrigatório.",
            example = "[{\"tipo\": \"CPF\", \"numero\": \"12345678901\"}, {\"tipo\": \"RG\", \"numero\": \"MG1234567\"}]", required = true)
    private Set<@Valid DocumentoDTO> documentos;

    @NotNull
    @Valid
    @Schema(description = "Endereço completo do usuário.",
            example = """
            {
                "estado": "MG",
                "cidade": "Belo Horizonte",
                "bairro": "Savassi",
                "rua": "Rua Pernambuco",
                "numero": "789",
                "codigoPostal": "30140-140",
                "informacoesAdicionais": "Prédio comercial, 3º andar"
            }
            """, required = true)
    private EnderecoDTO endereco;

    @Schema(description = "Telefones de contato do usuário.",
            example = "[{\"ddd\": \"11\", \"numero\": \"987654321\"}, {\"ddd\": \"11\", \"numero\": \"999888777\"}]")
    private Set<@Valid TelefoneDTO> telefones;
}
