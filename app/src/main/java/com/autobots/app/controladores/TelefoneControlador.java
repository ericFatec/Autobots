package com.autobots.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.app.entidades.Telefone;
import com.autobots.app.modelos.telefone.TelefoneAdicionarLinks;
import com.autobots.app.services.ClienteCrud;
import com.autobots.app.services.TelefoneCrud;
import com.autobots.app.types.dtos.TelefoneDTO;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

@RestController
@RequestMapping("/telefone")
public class TelefoneControlador {
    @Autowired
    private TelefoneCrud telefoneCrud;
    @Autowired
    private ClienteCrud clienteCrud;

    @Autowired
    private TelefoneAdicionarLinks adicionarLinks;

    @PostMapping("/{idCliente}")
    public ResponseEntity<?> adicionar(@PathVariable Long idCliente, @Valid @RequestBody Telefone telefone) {
        try {
            Telefone telefoneNovo = clienteCrud.inserirTelefone(idCliente, telefone);
            adicionarLinks.adicionarLink(telefoneNovo);
            return ResponseEntity.ok(telefoneNovo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao cadastrar telefone: " + e.getMessage());
        }
    }

    @GetMapping("/todos/{idCliente}")
    public ResponseEntity<?> selecionarFromCliente(@PathVariable Long idCliente) {
        try {
            List<Telefone> telefones = clienteCrud.selecionarTelefones(idCliente);
            adicionarLinks.adicionarLink(telefones);
            return ResponseEntity.ok(telefones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar telefones do cliente: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> selecionar(@PathVariable Long id) {
        try {
            Telefone telefone = telefoneCrud.selecionar(id);
            adicionarLinks.adicionarLink(telefone);
            return ResponseEntity.ok(telefone);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar telefone: " + e.getMessage());
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<?> selecionarTodos() {
        try {
            List<Telefone> telefones = telefoneCrud.selecionarTodos();
            adicionarLinks.adicionarLink(telefones);
            return ResponseEntity.ok(telefones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar telefones: " + e.getMessage());
        }
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody TelefoneDTO telefone) {
        try {
            Telefone telefoneAtualizado = telefoneCrud.atualizar(id, telefone);
            adicionarLinks.adicionarLink(telefoneAtualizado);
            return ResponseEntity.ok(telefoneAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao atualizar telefone: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            clienteCrud.removerTelefone(id);
            return ResponseEntity.ok("Telefone deletado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao deletar telefone: " + e.getMessage());
        }
    }
}
