package com.autobots.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.modelos.empresa.EmpresaInsercao;
import com.autobots.app.modelos.usuario.EmpresaInserirUsuario;
import com.autobots.app.modelos.usuario.UsuarioInsercao;
import com.autobots.app.types.dtos.EmpresaCadastroDTO;
import com.autobots.app.types.dtos.UsuarioCadastroDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class AppApplication {

	@Autowired
	private UsuarioInsercao usuarioInsercao;
	@Autowired
	private EmpresaInsercao empresaInsercao;
	@Autowired
	private EmpresaInserirUsuario empresaInserirUsuario;

	@Autowired
	private ObjectMapper mapper;

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@Bean
	public CommandLineRunner run() {
		return args -> {
			String empresa = """
					{
					"razaoSocial": "InovaTech Desenvolvimento de Sistemas S.A.",
					"nomeFantasia": "InovaTech",
					"telefones": [
						{
						"ddd": "31",
						"numero": "988888888"
						}
					],
					"endereco": {
						"estado": "MG",
						"cidade": "Belo Horizonte",
						"bairro": "Savassi",
						"rua": "Rua Fernandes Tourinho",
						"numero": "123",
						"codigoPostal": "30112-000",
						"informacoesAdicionais": "Sala 802, Edifício Platinum"
					}
					}
					""";
			String admin = """
			{
			"nome": "Ana Ribeiro",
			"nomeSocial": "Aninha",
			"email": [
				"ana.ribeiro@empresa.com",
				"a.ribeiro@gmail.com"
			],
			"login": "ana.ribeiro",
			"senha": "SenhaAna123!",
			"dataNascimento": "1985-03-10T00:00:00Z",
			"perfis": ["ADMIN"],
			"documentos": [
				{ "tipo": "CPF", "numero": "98765432100" },
				{ "tipo": "RG", "numero": "SP1122334" }
			],
			"endereco": {
				"estado": "SP",
				"cidade": "São Paulo",
				"bairro": "Pinheiros",
				"rua": "Rua dos Pinheiros",
				"numero": "321",
				"codigoPostal": "05422-010",
				"informacoesAdicionais": "Apartamento 1001"
			},
			"telefones": [
				{ "ddd": "11", "numero": "911223344" },
				{ "ddd": "11", "numero": "922334455" }
			]
			}
			""";
			String gerente = """
					{
					"nome": "Carlos Eduardo",
					"nomeSocial": "Cadu",
					"email": [
						"carlos.eduardo@empresa.com",
						"cadu.work@gmail.com"
					],
					"login": "carlos.eduardo",
					"senha": "SenhaCadu456!",
					"dataNascimento": "1990-08-22T00:00:00Z",
					"perfis": ["GERENTE", "VENDEDOR"],
					"documentos": [
						{ "tipo": "CPF", "numero": "12312312399" },
						{ "tipo": "RG", "numero": "RJ9876543" }
					],
					"endereco": {
						"estado": "RJ",
						"cidade": "Rio de Janeiro",
						"bairro": "Botafogo",
						"rua": "Rua Voluntários da Pátria",
						"numero": "450",
						"codigoPostal": "22270-010",
						"informacoesAdicionais": "Sala comercial 203"
					},
					"telefones": [
						{ "ddd": "21", "numero": "998877665" },
						{ "ddd": "21", "numero": "987654321" }
					]
					}
					""";
			String vendedor = """
					{
					"nome": "Beatriz Silva",
					"nomeSocial": "Bia",
					"email": [
						"beatriz.silva@loja.com",
						"bia.vendas@gmail.com"
					],
					"login": "beatriz.silva",
					"senha": "SenhaBia789!",
					"dataNascimento": "1995-11-30T00:00:00Z",
					"perfis": ["VENDEDOR", "CLIENTE"],
					"documentos": [
						{ "tipo": "CPF", "numero": "45678912300" },
						{ "tipo": "RG", "numero": "MG5566778" }
					],
					"endereco": {
						"estado": "MG",
						"cidade": "Uberlândia",
						"bairro": "Centro",
						"rua": "Av. Afonso Pena",
						"numero": "1500",
						"codigoPostal": "38400-700",
						"informacoesAdicionais": "Próximo ao shopping"
					},
					"telefones": [
						{ "ddd": "34", "numero": "912345678" },
						{ "ddd": "34", "numero": "934567890" }
					]
					}
					""";
			String cliente = """
					{
					"nome": "Daniel Costa",
					"nomeSocial": "Dani",
					"email": [
						"daniel.costa@email.com",
						"danicosta@gmail.com"
					],
					"login": "daniel.costa",
					"senha": "SenhaDani321!",
					"dataNascimento": "2000-05-05T00:00:00Z",
					"perfis": ["CLIENTE"],
					"documentos": [
						{ "tipo": "CPF", "numero": "78945612300" },
						{ "tipo": "RG", "numero": "BA3344556" }
					],
					"endereco": {
						"estado": "BA",
						"cidade": "Salvador",
						"bairro": "Barra",
						"rua": "Rua do Farol",
						"numero": "90",
						"codigoPostal": "40140-650",
						"informacoesAdicionais": "Casa azul"
					},
					"telefones": [
						{ "ddd": "71", "numero": "999888777" },
						{ "ddd": "71", "numero": "988776655" }
					]
					}
					""";
			UsuarioCadastroDTO adminDto = mapper.readValue(admin, UsuarioCadastroDTO.class);
			usuarioInsercao.inserir(adminDto);
			EmpresaCadastroDTO empresaDto = mapper.readValue(empresa, EmpresaCadastroDTO.class);
			Empresa empresaNova = empresaInsercao.inserir(empresaDto);
			UsuarioCadastroDTO gerenteDto = mapper.readValue(gerente, UsuarioCadastroDTO.class);
			empresaInserirUsuario.inserirUsuario(empresaNova, gerenteDto);
			UsuarioCadastroDTO vendedorDto = mapper.readValue(vendedor, UsuarioCadastroDTO.class);
			empresaInserirUsuario.inserirUsuario(empresaNova, vendedorDto);
			UsuarioCadastroDTO clienteDto = mapper.readValue(cliente, UsuarioCadastroDTO.class);
			usuarioInsercao.inserir(clienteDto);
		};
	}

}
