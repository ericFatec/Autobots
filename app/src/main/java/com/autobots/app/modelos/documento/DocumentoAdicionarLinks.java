package com.autobots.app.modelos.documento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.app.controladores.ClienteControlador;
import com.autobots.app.controladores.DocumentoControlador;
import com.autobots.app.entidades.Documento;
import com.autobots.app.repositorios.cliente.ClienteRepositorio;
import com.autobots.app.types.interfaces.LinkInterface;

@Component
public class DocumentoAdicionarLinks implements LinkInterface<Documento> {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public void adicionarLink(List<Documento> lista) {
        for (Documento documento : lista) {
            adicionarLink(documento);
        }
    }

    @Override
    public void adicionarLink(Documento documento) {
        long id = documento.getId();
        long idCliente = clienteRepositorio.findClienteIdByDocumentoId(id);

        // GET (Cliente)
        Link ownerLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(ClienteControlador.class).selecionar(idCliente))
            .withRel("cliente")
            .withType("GET");
        documento.add(ownerLink);

        // POST
        Link postLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(DocumentoControlador.class).adicionar(idCliente, null))
            .withRel("cadastrar")
            .withType("POST");
        documento.add(postLink);

        // GET (Self)
        Link selfLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(DocumentoControlador.class).selecionar(id))
            .withSelfRel()
            .withType("GET");
        documento.add(selfLink);

        // GET (Collection)
        Link allLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(DocumentoControlador.class).selecionarTodos())
            .withRel("documentos")
            .withType("GET");
        documento.add(allLink);

        // PATCH (Update)
        Link updateLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(DocumentoControlador.class).atualizar(id, null))
            .withRel("atualizar")
            .withType("PATCH");
        documento.add(updateLink);

        // DELETE
        Link deleteLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(DocumentoControlador.class).deletar(id))
            .withRel("deletar")
            .withType("DELETE");
        documento.add(deleteLink);
    }
}
