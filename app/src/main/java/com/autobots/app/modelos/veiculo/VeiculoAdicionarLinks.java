package com.autobots.app.modelos.veiculo;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.app.controladores.UsuarioControlador;
import com.autobots.app.controladores.VeiculoControlador;
import com.autobots.app.types.dtos.VeiculoReturnDTO;
import com.autobots.app.types.interfaces.LinkInterface;

@Component
public class VeiculoAdicionarLinks implements LinkInterface<VeiculoReturnDTO>{
    
    @Override
    public void adicionarLink(List<VeiculoReturnDTO> lista) {
        for (VeiculoReturnDTO veiculo : lista) {
            adicionarLink(veiculo);
        }
    }

    @Override
    public void adicionarLink(VeiculoReturnDTO veiculo) {
        Long id = veiculo.getId();
        Long idUsuario = veiculo.getUsuarioId();

        Link ownerLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(UsuarioControlador.class).selecionar(idUsuario))
            .withRel("usuario")
            .withType("GET");
        veiculo.add(ownerLink);

        Link selfLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(VeiculoControlador.class).selecionar(id))
            .withSelfRel()
            .withType("GET");
        veiculo.add(selfLink);

        Link allLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(VeiculoControlador.class).selecionarTodos())
            .withRel("veiculos")
            .withType("GET");
        veiculo.add(allLink);

        Link updateLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(VeiculoControlador.class).atualizar(id, null))
            .withRel("atualizar")
            .withType("PATCH");
        veiculo.add(updateLink);

        Link deleteLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(VeiculoControlador.class).deletar(id))
            .withRel("deletar")
            .withType("DELETE");
        veiculo.add(deleteLink);
    }
}
