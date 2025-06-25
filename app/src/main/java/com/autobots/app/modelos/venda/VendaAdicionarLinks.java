package com.autobots.app.modelos.venda;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.app.controladores.UsuarioControlador;
import com.autobots.app.controladores.VendaControlador;
import com.autobots.app.types.dtos.VendaReturnDTO;
import com.autobots.app.types.interfaces.LinkInterface;

@Component
public class VendaAdicionarLinks implements LinkInterface<VendaReturnDTO>{

    @Override
    public void adicionarLink(List<VendaReturnDTO> lista) {
        for (VendaReturnDTO venda : lista) {
            adicionarLink(venda);
        }
    }

    @Override
    public void adicionarLink(VendaReturnDTO venda) {
        Long id = venda.getId();
        Long idCliente = venda.getCliente() != null ? venda.getCliente().getUsuarioId() : null;
        Long idVendedor = venda.getVendedor() != null ? venda.getVendedor().getUsuarioId() : null;

        if (idCliente != null) {
            Link clienteLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(UsuarioControlador.class).selecionar(id))
                .withRel("cliente")
                .withType("GET");
            venda.add(clienteLink);
        }
        if (idVendedor != null) {
            Link vendedorLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(UsuarioControlador.class).selecionar(id))
                .withRel("vendedor")
                .withType("GET");
            venda.add(vendedorLink);
        }

        Link postLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(VendaControlador.class).adicionar(null))
			.withRel("cadastrar")
			.withType("POST");
		venda.add(postLink);

        Link selfLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(VendaControlador.class).selecionar(id))
			.withSelfRel()
			.withType("GET");
		venda.add(selfLink);

        Link allLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(VendaControlador.class).selecionarTodos())
			.withRel("vendas")
			.withType("GET");
		venda.add(allLink);

        Link deleteLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(VendaControlador.class).deletar(id))
			.withRel("deletar")
			.withType("DELETE");
        venda.add(deleteLink);
    }
    
}
