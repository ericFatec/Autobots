package com.autobots.app.modelos.empresa;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.app.controladores.EmpresaControlador;
import com.autobots.app.controladores.EnderecoControlador;
import com.autobots.app.controladores.MercadoriaControlador;
import com.autobots.app.controladores.ServicoControlador;
import com.autobots.app.controladores.TelefoneControlador;
import com.autobots.app.controladores.UsuarioControlador;
import com.autobots.app.controladores.VendaControlador;
import com.autobots.app.types.dtos.EmpresaReturnDTO;
import com.autobots.app.types.interfaces.LinkInterface;

@Component
public class EmpresaAdicionarLinks implements LinkInterface<EmpresaReturnDTO>{
    
    @Override
    public void adicionarLink(List<EmpresaReturnDTO> lista) {
        for (EmpresaReturnDTO empresa : lista) {
            adicionarLink(empresa);
        }
    }

    @Override
    public void adicionarLink(EmpresaReturnDTO empresa) {
        Long id = empresa.getId();
        Long idEndereco = empresa.getEndereco().getId();

        // GET (self)
        Link selfLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(EmpresaControlador.class).selecionar(id))
            .withSelfRel()
            .withType("GET");
        empresa.add(selfLink);

        // GET (collection)
        Link allLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(EmpresaControlador.class).selecionarTodos())
            .withRel("empresas")
            .withType("GET");
        empresa.add(allLink);

        // PATCH
        Link updateLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(EmpresaControlador.class).atualizar(id, null))
            .withRel("atualizar")
            .withType("PATCH");
        empresa.add(updateLink);

        // GET (Endereco)
		Link getAdrLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(EnderecoControlador.class).selecionar(idEndereco))
			.withRel("endereco")
			.withType("GET");
		empresa.add(getAdrLink);

        // GET (Telefones)
		Link getTelLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(TelefoneControlador.class).selecionarFromEmpresa(id))
			.withRel("telefones")
			.withType("GET");
		empresa.add(getTelLink);

		// POST (telefones)
        Link postTelLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(TelefoneControlador.class).adicionarNaEmpresa(id, null))
            .withRel("cadastrar-telefone")
            .withType("POST");
        empresa.add(postTelLink);

        // GET (usuarios)
		Link getUserLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(UsuarioControlador.class).selecionarFromEmpresa(id))
			.withRel("usuarios")
			.withType("GET");
		empresa.add(getUserLink);

        // POST (usuarios)
		Link postUserLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(UsuarioControlador.class).cadastrarNaEmpresa(id, null))
			.withRel("cadastrar-usuario")
			.withType("POST");
		empresa.add(postUserLink);

        // GET (mercadoria)
		Link getMerchLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(MercadoriaControlador.class).selecionarFromEmpresa(id))
            .withRel("mercadorias")
            .withType("GET");
        empresa.add(getMerchLink);

		// POST (mercadoria)
		Link postMerchLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(MercadoriaControlador.class).adicionarNaEmpresa(id, null))
            .withRel("cadastrar-mercadoria")
            .withType("POST");
        empresa.add(postMerchLink);

        // GET (servicos)
        Link getServiceLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(ServicoControlador.class).selecionarFromEmpresa(id))
            .withRel("servicos")
            .withType("GET");
        empresa.add(getServiceLink);

        // POST (servico)
        Link postServiceLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(ServicoControlador.class).adicionar(id, null))
            .withRel("cadastrar-servico")
            .withType("POST");
        empresa.add(postServiceLink);

        // GET (vendas)
		Link getVendasLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(VendaControlador.class).selecionarFromEmpresa(id))
			.withRel("vendas")
			.withType("GET");
		empresa.add(getVendasLink);

		// POST (venda)
		Link postVendaLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(VendaControlador.class).adicionar(null))
			.withRel("cadastrar-venda")
			.withType("POST");
		empresa.add(postVendaLink);

        // DELETE
        Link deleteLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(EmpresaControlador.class).deletar(id))
            .withRel("deletar")
            .withType("DELETE");
        empresa.add(deleteLink);
    }
}
