package com.autobots.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.autobots.app.entidades.Cliente;
import com.autobots.app.modelos.cliente.ClienteAdicionarLinks;
import com.autobots.app.services.ClienteCrud;
import com.autobots.app.types.dtos.ClienteAtualizadorDTO;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cliente")
public class ClienteControlador {
    @Autowired
    private ClienteCrud clienteCrud;

    @Autowired
    private ClienteAdicionarLinks adicionarLinks;

    @PostMapping("/cadastrar")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Cadastro de um novo cliente",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Cliente.class),
            examples = @ExampleObject(
                name = "Exemplo Cliente",
                summary = "Cliente completo",
                value = """
                {
                "nome": "Exemplo",
                "nomeSocial": "Junior",
                "dataNascimento": "1990-01-01T00:00:00Z",
                "documentos": [
                    {
                        "tipo": "RG",
                        "numero": "12345678"
                    }
                ],
                "endereco": {
                    "estado": "SP",
                    "cidade": "São Paulo",
                    "bairro": "Bela Vista",
                    "rua": "Av. Paulista",
                    "numero": "1000",
                    "codigoPostal": "01311-000"
                },
                "telefones": [
                    {
                    "ddd": "11",
                    "numero": "912345678"
                    }
                ]
                }
                """
            )
        )
    )
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Cliente cliente) {
        try {
            Cliente clienteNovo = clienteCrud.inserir(cliente);
            adicionarLinks.adicionarLink(clienteNovo);
            return ResponseEntity.ok(clienteNovo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> selecionar(@PathVariable Long id) {
        try {
            Cliente cliente = clienteCrud.selecionar(id);
            adicionarLinks.adicionarLink(cliente);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar cliente: " + e.getMessage());
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<?> selecionarTodos() {
        try {
            List<Cliente> clientes = clienteCrud.selecionarTodos();
            adicionarLinks.adicionarLink(clientes);
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar clientes: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody ClienteAtualizadorDTO novosDados) {
        try {
            Cliente cliente = clienteCrud.atualizar(id, novosDados);
            adicionarLinks.adicionarLink(cliente);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            clienteCrud.deletar(id);
            return ResponseEntity.ok("Cliente deletado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao deletar cliente: " + e.getMessage());
        }
    }
}
