package com.autobots.app.entidades.snapshots;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class MercadoriaSnapshot {
    private Long mercadoriaId;
    private String nome;
    private Double valor;
    private Integer quantidade;
}
