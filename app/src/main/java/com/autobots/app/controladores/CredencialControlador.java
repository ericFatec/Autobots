package com.autobots.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.app.modelos.credencial.CredencialAdicionarLinks;
import com.autobots.app.modelos.usuario.UsuarioAdicionarLinks;
import com.autobots.app.services.CredencialCrud;
import com.autobots.app.services.UsuarioCrud;
import com.autobots.app.types.dtos.CredencialDTO;
import com.autobots.app.types.dtos.CredencialReturnDTO;
import com.autobots.app.types.dtos.UsuarioReturnDTO;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

@RestController
@RequestMapping("/credencial")
public class CredencialControlador {
    @Autowired
    private CredencialCrud credencialCrud;
    @Autowired
    private UsuarioCrud usuarioCrud;

    @Autowired
    private CredencialAdicionarLinks adicionarLinks;
    @Autowired
    private UsuarioAdicionarLinks usuarioAdicionarLinks;

    @PostMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> adicionar(@PathVariable Long idUsuario, @Valid @RequestBody CredencialDTO credencialDTO) {
        try {
            CredencialReturnDTO novaCredencial = usuarioCrud.inserirCredencial(idUsuario, credencialDTO);
            adicionarLinks.adicionarLink(novaCredencial);
            return ResponseEntity.ok(novaCredencial);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao cadastrar credencial: " + e.getMessage());
        }
    }

    @PostMapping("/usuario/login")
    public ResponseEntity<?> login(@RequestBody CredencialDTO credencial) {
        try {
            UsuarioReturnDTO usuario = usuarioCrud.login(credencial);
            usuarioAdicionarLinks.adicionarLink(usuario);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao realizar login: " + e.getMessage());
        }
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<?> selecionar(@PathVariable Long id) {
        try {
            CredencialReturnDTO credencial = credencialCrud.selecionar(id);
            adicionarLinks.adicionarLink(credencial);
            return ResponseEntity.ok(credencial);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar credencial: " + e.getMessage());
        }
    }
    
    @GetMapping("/todos")
    public ResponseEntity<?> selecionarTodos() {
        try {
            List<CredencialReturnDTO> credenciais = credencialCrud.selecionarTodos();
            adicionarLinks.adicionarLink(credenciais);
            return ResponseEntity.ok(credenciais);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar credenciais: " + e.getMessage());
        }
    }

    @GetMapping("/usuario/todos/{idUsuario}")
    public ResponseEntity<?> selecionarFromUsuario(@PathVariable Long idUsuario) {
        try {
            List<CredencialReturnDTO> credenciais = credencialCrud.selecionarFromUsuario(idUsuario);
            adicionarLinks.adicionarLink(credenciais);
            return ResponseEntity.ok(credenciais);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar credenciais do usuário: " + e.getMessage());
        }
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody CredencialDTO novosDados) {
        try {
            CredencialReturnDTO credencial = credencialCrud.atualizar(id, novosDados);
            adicionarLinks.adicionarLink(credencial);
            return ResponseEntity.ok(credencial);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao atualizar credencial: " + e.getMessage());
        }
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<?> mudarStatus(@PathVariable Long id) {
        try {
            CredencialReturnDTO credencial = credencialCrud.mudarStatus(id);
            adicionarLinks.adicionarLink(credencial);
            return ResponseEntity.ok(credencial);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar credencial: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            credencialCrud.deletar(id);
            return ResponseEntity.ok("Credencial deletada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao deletar credencial: " + e.getMessage());
        }
    }
}
