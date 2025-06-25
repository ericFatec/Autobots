package com.autobots.app.types.dtos;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class ServicoReturnDTO extends RepresentationModel<ServicoReturnDTO>{
    private Long id;
    private String nome;
    private Double valor;
    private String descricao;
    private Long empresaId;
}
