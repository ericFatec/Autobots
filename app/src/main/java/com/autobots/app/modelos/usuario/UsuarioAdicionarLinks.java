package com.autobots.app.modelos.usuario;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.app.controladores.UsuarioControlador;
import com.autobots.app.controladores.VeiculoControlador;
import com.autobots.app.controladores.VendaControlador;
import com.autobots.app.controladores.CredencialControlador;
import com.autobots.app.controladores.DocumentoControlador;
import com.autobots.app.controladores.EmpresaControlador;
import com.autobots.app.controladores.EnderecoControlador;
import com.autobots.app.controladores.MercadoriaControlador;
import com.autobots.app.controladores.TelefoneControlador;
import com.autobots.app.types.dtos.UsuarioReturnDTO;
import com.autobots.app.types.interfaces.LinkInterface;

@Component
public class UsuarioAdicionarLinks implements LinkInterface<UsuarioReturnDTO> {

    @Override
	public void adicionarLink(List<UsuarioReturnDTO> lista) {
		for (UsuarioReturnDTO usuario : lista) {
			adicionarLink(usuario);
		}
	}

	@Override
	public void adicionarLink(UsuarioReturnDTO usuario) {
		Long id = usuario.getId();
		Long idEndereco = usuario.getEndereco().getId();
		Long idEmpresa = usuario.getEmpresaId();

		if (idEmpresa != null) {
			// GET (Empresa)
			Link companyLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(EmpresaControlador.class).selecionar(idEmpresa))
				.withSelfRel()
				.withType("GET");
			usuario.add(companyLink);
		}

		// GET (Self)
		Link selfLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(UsuarioControlador.class).selecionar(id))
			.withSelfRel()
			.withType("GET");
		usuario.add(selfLink);

		// GET (Collection)
		Link allLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(UsuarioControlador.class).selecionarTodos())
			.withRel("usuarios")
			.withType("GET");
		usuario.add(allLink);

		// PATCH
		Link updateLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(UsuarioControlador.class).atualizar(id, null))
			.withRel("atualizar")
			.withType("PATCH");
		usuario.add(updateLink);

		// GET (Documento)
		Link getDocLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(DocumentoControlador.class).selecionarFromUsuario(id))
			.withRel("documentos")
			.withType("GET");
		usuario.add(getDocLink);

		// POST
        Link postDocLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(DocumentoControlador.class).adicionar(id, null))
            .withRel("cadastrar-documento")
            .withType("POST");
        usuario.add(postDocLink);

		// GET (Endereco)
		Link getAdrLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(EnderecoControlador.class).selecionar(idEndereco))
			.withRel("endereco")
			.withType("GET");
		usuario.add(getAdrLink);

		// GET (Telefones)
		Link getTelLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(TelefoneControlador.class).selecionarFromUsuario(id))
			.withRel("telefones")
			.withType("GET");
		usuario.add(getTelLink);

		// POST
        Link postTelLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(TelefoneControlador.class).adicionarNoUsuario(id, null))
            .withRel("cadastrar-telefone")
            .withType("POST");
        usuario.add(postTelLink);

		// GET (Credenciais)
		Link getCredLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(CredencialControlador.class).selecionarFromUsuario(id))
			.withRel("credenciais")
			.withType("GET");
		usuario.add(getCredLink);

		// POST (credencial)
        Link postCredLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(CredencialControlador.class).adicionar(id, null))
            .withRel("cadastrar-credencial")
            .withType("POST");
        usuario.add(postCredLink);

		// GET (mercadoria)
		Link getMerchLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(MercadoriaControlador.class).selecionarFromUsuario(id))
            .withRel("mercadorias")
            .withType("GET");
        usuario.add(getMerchLink);

		// POST (mercadoria)
		Link postMerchLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(MercadoriaControlador.class).adicionarNoUsuario(id, null))
            .withRel("cadastrar-mercadoria")
            .withType("POST");
        usuario.add(postMerchLink);

		// GET (veiculos)
		Link getVeiculoLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(VeiculoControlador.class).selecionarFromUsuario(id))
            .withRel("veiculos")
            .withType("GET");
        usuario.add(getVeiculoLink);

		// POST (veiculo)
		Link postVeiculoLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(VeiculoControlador.class).adicionar(id, null))
            .withRel("cadastrar-veiculo")
            .withType("POST");
        usuario.add(postVeiculoLink);

		// GET (vendas)
		Link getVendasLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(VendaControlador.class).selecionarFromUsuario(id))
			.withRel("vendas")
			.withType("GET");
		usuario.add(getVendasLink);

		// POST (venda)
		Link postVendaLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(VendaControlador.class).adicionar(null))
			.withRel("cadastrar-venda")
			.withType("POST");
		usuario.add(postVendaLink);

		// DELETE
		Link deleteLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(UsuarioControlador.class).deletar(id))
			.withRel("deletar")
			.withType("DELETE");
		usuario.add(deleteLink);
	}
}
