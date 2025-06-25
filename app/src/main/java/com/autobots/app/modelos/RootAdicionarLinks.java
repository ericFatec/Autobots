package com.autobots.app.modelos;

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
import com.autobots.app.controladores.RootControlador;
import com.autobots.app.controladores.RootControlador.ApiRoot;
import com.autobots.app.controladores.ServicoControlador;
import com.autobots.app.controladores.TelefoneControlador;
import com.autobots.app.types.interfaces.LinkInterface;

@Component
public class RootAdicionarLinks implements LinkInterface<ApiRoot>{

    public void adicionarLink(List<ApiRoot> __){
        // método não utilizado para ApiRoot
    }

    @Override
    public void adicionarLink(ApiRoot root) {
        // GET all empresas
        Link getEmpresas = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(EmpresaControlador.class).selecionarTodos())
            .withRel("empresas")
            .withType("GET");
        root.add(getEmpresas);

        // POST usuario
        Link postEmpresa = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(EmpresaControlador.class).cadastrar(null))
			.withRel("cadastro-empresa")
            .withType("POST");
		root.add(postEmpresa);

        // GET all usuarios
        Link getUsuarios = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(UsuarioControlador.class).selecionarTodos())
			.withRel("usuarios")
            .withType("GET");
		root.add(getUsuarios);

        // POST usuario
        Link postUsuario = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(UsuarioControlador.class).cadastrar(null))
			.withRel("cadastro-usuario")
            .withType("POST");
		root.add(postUsuario);

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

        // GET all credenciais
        Link getCredenciais = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(CredencialControlador.class).selecionarTodos())
            .withRel("credenciais")
            .withType("GET");
        root.add(getCredenciais);

        // GET all mercadorias
        Link getMercadorias = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(MercadoriaControlador.class).selecionarTodos())
            .withRel("mercadorias")
            .withType("GET");
        root.add(getMercadorias);

        // GET all servicos
        Link getServicos = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(ServicoControlador.class).selecionarTodos())
            .withRel("servicos")
            .withType("GET");
        root.add(getServicos);

        // GET all veiculos
        Link getVeiculos = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(VeiculoControlador.class).selecionarTodos())
            .withRel("veiculos")
            .withType("GET");
        root.add(getVeiculos);

        // GET all vendas
        Link allLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(VendaControlador.class).selecionarTodos())
			.withRel("vendas")
			.withType("GET");
		root.add(allLink);

        // Self link
        Link selfLink = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder.methodOn(RootControlador.class).root())
			.withSelfRel()
            .withType("GET");
		root.add(selfLink);
    }
}
