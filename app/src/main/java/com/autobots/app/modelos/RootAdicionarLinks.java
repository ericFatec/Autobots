package com.autobots.app.modelos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.app.controladores.ClienteControlador;
import com.autobots.app.controladores.DocumentoControlador;
import com.autobots.app.controladores.EnderecoControlador;
import com.autobots.app.controladores.RootControlador;
import com.autobots.app.controladores.RootControlador.ApiRoot;
import com.autobots.app.controladores.TelefoneControlador;
import com.autobots.app.types.interfaces.LinkInterface;

@Component
public class RootAdicionarLinks implements LinkInterface<ApiRoot>{

    public void adicionarLink(List<ApiRoot> __){
        // método não utilizado para ApiRoot
    }

    @Override
    public void adicionarLink(ApiRoot root) {
        // GET all clientes
        Link getClientes = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(ClienteControlador.class).selecionarTodos())
			.withRel("clientes")
            .withType("GET");
		root.add(getClientes);

        // POST cliente
        Link postLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(ClienteControlador.class).cadastrar(null))
			.withRel("cadastro_cliente")
            .withType("POST");
		root.add(postLink);

        // GET all documentos
        Link getDocumentos = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(DocumentoControlador.class).selecionarTodos())
            .withRel("documentos")
            .withType("GET");
        root.add(getDocumentos);

        // GET all enderecos
        Link getEnderecos = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(EnderecoControlador.class).selecionarTodos())
            .withRel("enderecos")
            .withType("GET");
        root.add(getEnderecos);

        // GET all telefones
        Link getTelefones = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(TelefoneControlador.class).selecionarTodos())
            .withRel("telefones")
            .withType("GET");
        root.add(getTelefones);

        // Self link
        Link selfLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(RootControlador.class).root())
			.withSelfRel()
            .withType("GET");
		root.add(selfLink);
    }
}
