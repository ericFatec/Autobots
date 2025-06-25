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

import com.autobots.app.modelos.telefone.TelefoneAdicionarLinks;
import com.autobots.app.services.UsuarioCrud;
import com.autobots.app.services.EmpresaCrud;
import com.autobots.app.services.TelefoneCrud;
import com.autobots.app.types.dtos.TelefoneDTO;
import com.autobots.app.types.dtos.TelefoneReturnDTO;

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
    private UsuarioCrud usuarioCrud;
    @Autowired
    private EmpresaCrud empresaCrud;

    @Autowired
    private TelefoneAdicionarLinks adicionarLinks;

    @PostMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> adicionarNoUsuario(@PathVariable Long idUsuario, @Valid @RequestBody TelefoneDTO telefone) {
        try {
            TelefoneReturnDTO telefoneNovo = usuarioCrud.inserirTelefone(idUsuario, telefone);
            adicionarLinks.adicionarLink(telefoneNovo);
            return ResponseEntity.ok(telefoneNovo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao cadastrar telefone: " + e.getMessage());
        }
    }

    @PostMapping("/empresa/{idEmpresa}")
    public ResponseEntity<?> adicionarNaEmpresa(@PathVariable Long idEmpresa, @Valid @RequestBody TelefoneDTO telefone) {
        try {
            TelefoneReturnDTO telefoneNovo = empresaCrud.inserirTelefone(idEmpresa, telefone);
            adicionarLinks.adicionarLink(telefoneNovo);
            return ResponseEntity.ok(telefoneNovo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao cadastrar telefone: " + e.getMessage());
        }
    }

    @GetMapping("/usuario/todos/{idUsuario}")
    public ResponseEntity<?> selecionarFromUsuario(@PathVariable Long idUsuario) {
        try {
            List<TelefoneReturnDTO> telefones = telefoneCrud.selecionarFromUsuario(idUsuario);
            adicionarLinks.adicionarLink(telefones);
            return ResponseEntity.ok(telefones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar telefones do usuario: " + e.getMessage());
        }
    }
    
    @GetMapping("/empresa/todos/{idEmpresa}")
    public ResponseEntity<?> selecionarFromEmpresa(@PathVariable Long idEmpresa) {
        try {
            List<TelefoneReturnDTO> telefones = telefoneCrud.selecionarFromEmpresa(idEmpresa);
            adicionarLinks.adicionarLink(telefones);
            return ResponseEntity.ok(telefones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar telefones do usuario: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> selecionar(@PathVariable Long id) {
        try {
            TelefoneReturnDTO telefone = telefoneCrud.selecionar(id);
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
            List<TelefoneReturnDTO> telefones = telefoneCrud.selecionarTodos();
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
            TelefoneReturnDTO telefoneAtualizado = telefoneCrud.atualizar(id, telefone);
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
            telefoneCrud.deletar(id);
            return ResponseEntity.ok("Telefone deletado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao deletar telefone: " + e.getMessage());
        }
    }
}
