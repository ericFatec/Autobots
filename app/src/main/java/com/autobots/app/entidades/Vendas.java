package com.autobots.app.entidades;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.autobots.app.entidades.snapshots.MercadoriaSnapshot;
import com.autobots.app.entidades.snapshots.ServicoSnapshot;
import com.autobots.app.entidades.snapshots.UsuarioSnapshot;
import com.autobots.app.entidades.snapshots.VeiculoSnapshot;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class Vendas{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private Instant dataCadastro;
    
    @Column(nullable = false, unique = true)
    private String identificacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    @Embedded
    private UsuarioSnapshot usuarioDeletado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id")
    private Usuario vendedor;

    @Column(nullable = false)
    private Double valorTotal;

    @ElementCollection
    @CollectionTable(name = "venda_mercadorias",
            joinColumns = @JoinColumn(name = "venda_id"))
    private Set<MercadoriaSnapshot> mercadorias = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "venda_servicos",
            joinColumns = @JoinColumn(name = "venda_id"))
    private Set<ServicoSnapshot> servicos = new HashSet<>();

    @Embedded
    private VeiculoSnapshot veiculo;
}
