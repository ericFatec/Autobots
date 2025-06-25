package com.autobots.app.modelos.credencial;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.app.controladores.CredencialControlador;
import com.autobots.app.controladores.UsuarioControlador;
import com.autobots.app.types.dtos.CredencialReturnDTO;
import com.autobots.app.types.interfaces.LinkInterface;

@Component
public class CredencialAdicionarLinks implements LinkInterface<CredencialReturnDTO>{
    
    @Override
    public void adicionarLink(List<CredencialReturnDTO> lista) {
        for (CredencialReturnDTO credencial : lista) {
            adicionarLink(credencial);
        }
    }

    @Override
    public void adicionarLink(CredencialReturnDTO credencial) {
        Long id = credencial.getId();
        Long idUsuario = credencial.getUsuarioId();

        // GET (usuario)
        Link ownerLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(UsuarioControlador.class).selecionar(idUsuario))
            .withRel("usuario")
            .withType("GET");
        credencial.add(ownerLink);

        // GET (self)
        Link selfLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(CredencialControlador.class).selecionar(id))
            .withSelfRel()
            .withType("GET");
        credencial.add(selfLink);

        // GET (Collection)
        Link allLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(CredencialControlador.class).selecionarTodos())
            .withRel("credenciais")
            .withType("GET");
        credencial.add(allLink);

        // PATCH (Update)
        Link updateLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(CredencialControlador.class).atualizar(id, null))
            .withRel("atualizar")
            .withType("PATCH");
        credencial.add(updateLink);

        // PATCH (activate/deactivate)
        Link statusLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(CredencialControlador.class).mudarStatus(id))
            .withRel("ativar-desativar")
            .withType("PATCH");
        credencial.add(statusLink);

        // DELETE
        Link deleteLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(CredencialControlador.class).deletar(id))
            .withRel("deletar")
            .withType("DELETE");
        credencial.add(deleteLink);
    }
}
