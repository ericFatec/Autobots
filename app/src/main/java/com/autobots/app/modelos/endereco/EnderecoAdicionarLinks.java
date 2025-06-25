package com.autobots.app.modelos.endereco;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.app.controladores.ClienteControlador;
import com.autobots.app.controladores.EnderecoControlador;
import com.autobots.app.entidades.Endereco;
import com.autobots.app.repositorios.cliente.ClienteRepositorio;
import com.autobots.app.types.interfaces.LinkInterface;

@Component
public class EnderecoAdicionarLinks implements LinkInterface<Endereco> {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public void adicionarLink(List<Endereco> lista) {
        for (Endereco endereco : lista) {
            adicionarLink(endereco);
        }
    }

    @Override
    public void adicionarLink(Endereco endereco) {
        long id = endereco.getId();
        long idCliente = clienteRepositorio.findClienteIdByEnderecoId(id);

        // GET (Cliente)
        Link ownerLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(ClienteControlador.class).selecionar(idCliente))
            .withRel("cliente")
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
