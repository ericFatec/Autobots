package com.autobots.app.modelos.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.app.controladores.ClienteControlador;
import com.autobots.app.controladores.DocumentoControlador;
import com.autobots.app.controladores.EnderecoControlador;
import com.autobots.app.controladores.TelefoneControlador;
import com.autobots.app.entidades.Cliente;
import com.autobots.app.repositorios.cliente.ClienteRepositorio;
import com.autobots.app.types.interfaces.LinkInterface;

@Component
public class ClienteAdicionarLinks implements LinkInterface<Cliente> {

	@Autowired
	ClienteRepositorio clienteRepositorio;

    @Override
	public void adicionarLink(List<Cliente> lista) {
		for (Cliente cliente : lista) {
			adicionarLink(cliente);
		}
	}

	@Override
	public void adicionarLink(Cliente cliente) {
		long id = cliente.getId();
		long idEndereco = clienteRepositorio.findEnderecoIdByClienteId(id);

		// GET (Self)
		Link selfLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(ClienteControlador.class).selecionar(id))
			.withSelfRel()
			.withType("GET");
		cliente.add(selfLink);

		// GET (Collection)
		Link allLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(ClienteControlador.class).selecionarTodos())
			.withRel("clientes")
			.withType("GET");
		cliente.add(allLink);

		// PATCH
		Link updateLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(ClienteControlador.class).atualizar(id, null))
			.withRel("atualizar")
			.withType("PATCH");
		cliente.add(updateLink);

		// GET (Documento)
		Link getDocLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(DocumentoControlador.class).selecionarFromCliente(id))
			.withRel("documentos")
			.withType("GET");
		cliente.add(getDocLink);

		// GET (Endereco)
		Link getAdrLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(EnderecoControlador.class).selecionar(idEndereco))
			.withRel("endereco")
			.withType("GET");
		cliente.add(getAdrLink);

		// GET (Telefones)
		Link getTelLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(TelefoneControlador.class).selecionarFromCliente(id))
			.withRel("telefones")
			.withType("GET");
		cliente.add(getTelLink);

		// DELETE
		Link deleteLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(ClienteControlador.class).deletar(id))
			.withRel("deletar")
			.withType("DELETE");
		cliente.add(deleteLink);
	}
}
