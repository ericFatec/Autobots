package com.autobots.app.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;

@Data
@Entity
public class EmpresaEstoque {
    @EmbeddedId
    private EmpresaEstoqueId id = new EmpresaEstoqueId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("empresaId")
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("mercadoriaId")
    @JoinColumn(name = "mercadoria_id")
    private Mercadoria mercadoria;

    @Column(nullable = false)
    private Integer quantidade;
}
