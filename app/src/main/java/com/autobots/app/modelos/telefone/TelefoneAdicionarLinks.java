package com.autobots.app.modelos.telefone;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.app.controladores.UsuarioControlador;
import com.autobots.app.controladores.EmpresaControlador;
import com.autobots.app.controladores.TelefoneControlador;
import com.autobots.app.types.dtos.TelefoneReturnDTO;
import com.autobots.app.types.interfaces.LinkInterface;

@Component
public class TelefoneAdicionarLinks implements LinkInterface<TelefoneReturnDTO> {

    @Override
    public void adicionarLink(List<TelefoneReturnDTO> lista) {
        for (TelefoneReturnDTO telefone : lista) {
            adicionarLink(telefone);
        }
    }

    @Override
    public void adicionarLink(TelefoneReturnDTO telefone) {
        Long id = telefone.getId();
        Long idUsuario = telefone.getUsuarioId();
        Long idEmpresa = telefone.getEmpresaId();

        if (idEmpresa != null) {
            // GET (empresa)
            Link ownerLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(EmpresaControlador.class).selecionar(idEmpresa))
                .withRel("empresa")
                .withType("GET");
            telefone.add(ownerLink);
        } else {
            // GET (usuario)
            Link ownerLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(UsuarioControlador.class).selecionar(idUsuario))
                .withRel("usuario")
                .withType("GET");
            telefone.add(ownerLink);
        }

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
