package com.autobots.app.types.dtos;

import java.time.Instant;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class MercadoriaReturnDTO extends RepresentationModel<MercadoriaReturnDTO>{
    private Long id;
    private String nome;
    private Instant dataValidade;
    private Instant dataFabricacao;
    private Instant dataCadastro;
    private Double valor;
    private String descricao;
    private Integer quantidade;
    private Long usuarioId;
    private Long empresaId;
}
