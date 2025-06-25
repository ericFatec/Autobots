package com.autobots.app.types.dtos;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.app.types.enums.TipoVeiculo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class VeiculoReturnDTO extends RepresentationModel<VeiculoReturnDTO>{
    private Long id;
    private TipoVeiculo tipo;
    private String modelo;
    private String placa;
    private Long usuarioId;
}
