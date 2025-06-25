package com.autobots.app.types.dtos;

import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para cadastro de uma nova empresa com informações básicas.")
public class EmpresaCadastroDTO {

    @NotBlank
    @Schema(description = "Razão social da empresa (nome legal registrado). Obrigatório e único!",
            example = "Autobots Soluções Tecnológicas LTDA", required = true)
    private String razaoSocial;

    @Schema(description = "Nome fantasia da empresa.",
            example = "Autobots Tech")
    private String nomeFantasia;

    @Schema(description = "Telefones de contato da empresa.",
            example = "[{\"ddd\": \"11\", \"numero\": \"999999999\"}]")
    private Set<@Valid TelefoneDTO> telefones;

    @NotNull
    @Schema(description = "Endereço completo da empresa. Obrigatório!",
            example = """
            {
                "estado": "RJ",
                "cidade": "Rio de Janeiro",
                "bairro": "Copacabana",
                "rua": "Avenida Atlântica",
                "numero": "456",
                "codigoPostal": "22070-000",
                "informacoesAdicionais": "Próximo ao hotel"
            }
            """, required = true)
    private EnderecoDTO endereco;
}