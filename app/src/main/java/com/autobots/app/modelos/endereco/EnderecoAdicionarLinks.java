package com.autobots.app.modelos.endereco;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.app.controladores.UsuarioControlador;
import com.autobots.app.controladores.EnderecoControlador;
import com.autobots.app.types.dtos.EnderecoReturnDTO;
import com.autobots.app.types.interfaces.LinkInterface;

@Component
public class EnderecoAdicionarLinks implements LinkInterface<EnderecoReturnDTO> {

    @Override
    public void adicionarLink(List<EnderecoReturnDTO> lista) {
        for (EnderecoReturnDTO endereco : lista) {
            adicionarLink(endereco);
        }
    }

    @Override
    public void adicionarLink(EnderecoReturnDTO endereco) {
        Long id = endereco.getId();
        Long idUsuario = endereco.getUsuarioId();

        // GET (usuario)
        Link ownerLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(UsuarioControlador.class).selecionar(idUsuario))
            .withRel("usuario")
            .withType("GET");
        endereco.add(ownerLink);

        // GET (Self)
        Link selfLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(EnderecoControlador.class).selecionar(id))
            .withSelfRel()
            .withType("GET");
        endereco.add(selfLink);

        // GET (Collection)
        Link allLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(EnderecoControlador.class).selecionarTodos())
            .withRel("enderecos")
            .withType("GET");
        endereco.add(allLink);

        // PATCH (Update)
        Link updateLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(EnderecoControlador.class).atualizar(id, null))
            .withRel("atualizar")
            .withType("PATCH");
        endereco.add(updateLink);
    }
}
