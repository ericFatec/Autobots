package com.autobots.app.entidades.snapshots;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import lombok.Data;

@Data
@Embeddable
public class UsuarioSnapshot {
    private Long usuarioId;
    private String nome;
    private Set<String> email;

    @ElementCollection
    @CollectionTable(
        name = "usuario_snapshot_telefones",
        joinColumns = @JoinColumn(name = "venda_id")
    )
    private Set<TelefoneSnapshot> telefones;

    private EnderecoSnapshot endereco;
    private String empresa;
}
