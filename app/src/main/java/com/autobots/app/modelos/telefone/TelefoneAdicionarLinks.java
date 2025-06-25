package com.autobots.app.modelos.telefone;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.app.controladores.ClienteControlador;
import com.autobots.app.controladores.TelefoneControlador;
import com.autobots.app.entidades.Telefone;
import com.autobots.app.repositorios.cliente.ClienteRepositorio;
import com.autobots.app.types.interfaces.LinkInterface;

@Component
public class TelefoneAdicionarLinks implements LinkInterface<Telefone> {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public void adicionarLink(List<Telefone> lista) {
        for (Telefone telefone : lista) {
            adicionarLink(telefone);
        }
    }

    @Override
    public void adicionarLink(Telefone telefone) {
        long id = telefone.getId();
        long idCliente = clienteRepositorio.findClienteIdByTelefoneId(id);

        // GET (Cliente)
        Link ownerLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(ClienteControlador.class).selecionar(idCliente))
            .withRel("cliente")
            .withType("GET");
        telefone.add(ownerLink);

        // POST
        Link postLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(TelefoneControlador.class).adicionar(idCliente, null))
            .withRel("cadastrar")
            .withType("POST");
        telefone.add(postLink);

        // GET (Self)
        Link selfLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(TelefoneControlador.class).selecionar(id))
            .withSelfRel()
            .withType("GET");
        telefone.add(selfLink);

        // GET (Collection)
        Link allLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(TelefoneControlador.class).selecionarTodos())
            .withRel("telefones")
            .withType("GET");
        telefone.add(allLink);

        // PATCH (Update)
        Link updateLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(TelefoneControlador.class).atualizar(id, null))
            .withRel("atualizar")
            .withType("PATCH");
        telefone.add(updateLink);

        // DELETE
        Link deleteLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(TelefoneControlador.class).deletar(id))
            .withRel("deletar")
            .withType("DELETE");
        telefone.add(deleteLink);
    }
}
