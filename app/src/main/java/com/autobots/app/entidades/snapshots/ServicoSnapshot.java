package com.autobots.app.entidades.snapshots;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ServicoSnapshot {
    private Long servicoId;
    private String nome;
    private Double valor;
}
