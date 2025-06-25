package com.autobots.app.modelos.documento;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.app.controladores.UsuarioControlador;
import com.autobots.app.controladores.DocumentoControlador;
import com.autobots.app.types.dtos.DocumentoReturnDTO;
import com.autobots.app.types.interfaces.LinkInterface;

@Component
public class DocumentoAdicionarLinks implements LinkInterface<DocumentoReturnDTO> {

    @Override
    public void adicionarLink(List<DocumentoReturnDTO> lista) {
        for (DocumentoReturnDTO documento : lista) {
            adicionarLink(documento);
        }
    }

    @Override
    public void adicionarLink(DocumentoReturnDTO documento) {
        Long id = documento.getId();
        Long idUsuario = documento.getUsuarioId();

        // GET (usuario)
        Link ownerLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(UsuarioControlador.class).selecionar(idUsuario))
            .withRel("usuario")
            .withType("GET");
        documento.add(ownerLink);

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
