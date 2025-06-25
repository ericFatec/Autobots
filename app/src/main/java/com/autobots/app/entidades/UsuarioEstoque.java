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
public class UsuarioEstoque {
    @EmbeddedId
    private UsuarioEstoqueId id = new UsuarioEstoqueId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("mercadoriaId")
    @JoinColumn(name = "mercadoria_id")
    private Mercadoria mercadoria;

    @Column(nullable = false)
    private Integer quantidade;
}
