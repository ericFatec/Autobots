package com.autobots.app.types.dtos;

import java.time.Instant;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.app.entidades.snapshots.MercadoriaSnapshot;
import com.autobots.app.entidades.snapshots.ServicoSnapshot;
import com.autobots.app.entidades.snapshots.UsuarioSnapshot;
import com.autobots.app.entidades.snapshots.VeiculoSnapshot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class VendaReturnDTO extends RepresentationModel<VendaReturnDTO>{
    private Long id;
    private Instant dataCadastro;
    private String identificacao;
    private UsuarioSnapshot cliente;
    private UsuarioSnapshot vendedor;
    private Double valorTotal;
    private Set<MercadoriaSnapshot> mercadorias;
    private Set<ServicoSnapshot> servicos;
    private VeiculoSnapshot veiculo;
}
