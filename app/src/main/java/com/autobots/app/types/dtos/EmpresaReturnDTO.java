package com.autobots.app.types.dtos;

import java.time.Instant;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class EmpresaReturnDTO extends RepresentationModel<EmpresaReturnDTO>{
    private Long id;
    private String razaoSocial;
    private String nomeFantasia;
    private Set<TelefoneReturnDTO> telefones;
    private EnderecoReturnDTO endereco;
    private Instant dataCadastro;
}
