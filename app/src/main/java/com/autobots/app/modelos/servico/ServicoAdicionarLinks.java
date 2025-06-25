package com.autobots.app.modelos.servico;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.app.controladores.EmpresaControlador;
import com.autobots.app.controladores.ServicoControlador;
import com.autobots.app.types.dtos.ServicoReturnDTO;
import com.autobots.app.types.interfaces.LinkInterface;

@Component
public class ServicoAdicionarLinks implements LinkInterface<ServicoReturnDTO>{
    
    @Override
    public void adicionarLink(List<ServicoReturnDTO> lista) {
        for (ServicoReturnDTO servico : lista) {
            adicionarLink(servico);
        }
    }

    @Override
    public void adicionarLink(ServicoReturnDTO servico) {
        Long id = servico.getId();
        Long idEmpresa = servico.getEmpresaId();

        Link ownerLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(EmpresaControlador.class).selecionar(idEmpresa))
            .withRel("empresa")
            .withType("GET");
        servico.add(ownerLink);

        Link selfLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(ServicoControlador.class).selecionar(id))
            .withSelfRel()
            .withType("GET");
        servico.add(selfLink);

        Link allLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(ServicoControlador.class).selecionarTodos())
            .withRel("servicos")
            .withType("GET");
        servico.add(allLink);

        Link updateLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(ServicoControlador.class).atualizar(id, null))
            .withRel("atualizar")
            .withType("PATCH");
        servico.add(updateLink);

        Link deleteLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(ServicoControlador.class).deletar(id))
            .withRel("deletar")
            .withType("DELETE");
        servico.add(deleteLink);
    }
}
