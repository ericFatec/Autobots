package com.autobots.app.types.dtos;

import java.time.Instant;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.app.types.enums.TipoPerfil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UsuarioReturnDTO extends RepresentationModel<UsuarioReturnDTO>{
    private Long id;
    private Long empresaId;
    private String nome;
    private String nomeSocial;
    private Set<String> email;
    private Instant dataNascimento;
    private Set<TipoPerfil> perfis;
    private Instant dataCadastro;
    private Set<DocumentoReturnDTO> documentos;
    private EnderecoReturnDTO endereco;
    private Set<TelefoneReturnDTO> telefones;
}
