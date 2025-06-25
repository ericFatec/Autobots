package com.autobots.app.entidades.snapshots;

import com.autobots.app.types.enums.TipoVeiculo;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class VeiculoSnapshot {
    private Long veiculoId;
    private TipoVeiculo tipo;
    private String modelo;
    private String placa;
}
