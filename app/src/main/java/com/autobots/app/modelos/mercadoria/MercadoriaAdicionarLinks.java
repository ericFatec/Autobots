package com.autobots.app.modelos.mercadoria;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.app.controladores.EmpresaControlador;
import com.autobots.app.controladores.MercadoriaControlador;
import com.autobots.app.controladores.UsuarioControlador;
import com.autobots.app.types.dtos.MercadoriaReturnDTO;
import com.autobots.app.types.interfaces.LinkInterface;

@Component
public class MercadoriaAdicionarLinks implements LinkInterface<MercadoriaReturnDTO>{
    
    @Override
    public void adicionarLink(List<MercadoriaReturnDTO> lista) {
        for (MercadoriaReturnDTO mercadoria : lista) {
            adicionarLink(mercadoria);
        }
    }

    @Override
    public void adicionarLink(MercadoriaReturnDTO mercadoria) {
        Long id = mercadoria.getId();
        Long idUsuario = mercadoria.getUsuarioId();
        Long idEmpresa = mercadoria.getEmpresaId();

        if (idEmpresa != null) {
            Link ownerLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(EmpresaControlador.class).selecionar(idEmpresa))
                .withRel("empresa")
                .withType("GET");
            mercadoria.add(ownerLink);
        } else {
            Link ownerLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(UsuarioControlador.class).selecionar(idUsuario))
                .withRel("usuario")
                .withType("GET");
            mercadoria.add(ownerLink);
        }

        Link selfLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(MercadoriaControlador.class).selecionar(id))
            .withSelfRel()
            .withType("GET");
        mercadoria.add(selfLink);

        Link allLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(MercadoriaControlador.class).selecionarTodos())
            .withRel("mercadorias")
            .withType("GET");
        mercadoria.add(allLink);

        Link updateLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(MercadoriaControlador.class).atualizar(id, null))
            .withRel("atualizar")
            .withType("PATCH");
        mercadoria.add(updateLink);

        Link updateSupplyLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(MercadoriaControlador.class).atualizarEstoque(id, null))
            .withRel("atualizar-estoque")
            .withType("PATCH");
        mercadoria.add(updateSupplyLink);

        Link deleteLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(MercadoriaControlador.class).deletar(id))
            .withRel("deletar")
            .withType("DELETE");
        mercadoria.add(deleteLink);
    }
}
