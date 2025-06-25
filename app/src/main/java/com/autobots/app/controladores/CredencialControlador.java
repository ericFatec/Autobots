package com.autobots.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.app.modelos.RequesterBuilder;
import com.autobots.app.modelos.credencial.CredencialAdicionarLinks;
import com.autobots.app.services.CredencialCrud;
import com.autobots.app.services.UsuarioCrud;
import com.autobots.app.types.dtos.CredencialDTO;
import com.autobots.app.types.dtos.CredencialReturnDTO;
import com.autobots.app.types.dtos.RequesterDTO;

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
    private RequesterBuilder requesterBuilder;

    @PostMapping("/usuario/{idUsuario}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    public ResponseEntity<?> adicionar(@PathVariable Long idUsuario, @Valid @RequestBody CredencialDTO credencialDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            CredencialReturnDTO novaCredencial = usuarioCrud.inserirCredencial(idUsuario, credencialDTO, requester);
            adicionarLinks.adicionarLink(novaCredencial);
            return ResponseEntity.ok(novaCredencial);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao cadastrar credencial: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> selecionar(@PathVariable Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            CredencialReturnDTO credencial = credencialCrud.selecionar(id, requester);
            adicionarLinks.adicionarLink(credencial);
            return ResponseEntity.ok(credencial);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar credencial: " + e.getMessage());
        }
    }
    
    @GetMapping("/todos")
    @PreAuthorize("hasRole('ADMIN')")
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

    @GetMapping("/todos/{idUsuario}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> selecionarFromUsuario(@PathVariable Long idUsuario) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            List<CredencialReturnDTO> credenciais = credencialCrud.selecionarFromUsuario(idUsuario, requester);
            adicionarLinks.adicionarLink(credenciais);
            return ResponseEntity.ok(credenciais);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar credenciais do usuário: " + e.getMessage());
        }
    }
    
    @PatchMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody CredencialDTO novosDados) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            CredencialReturnDTO credencial = credencialCrud.atualizar(id, novosDados, requester);
            adicionarLinks.adicionarLink(credencial);
            return ResponseEntity.ok(credencial);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao atualizar credencial: " + e.getMessage());
        }
    }

    @PatchMapping("/status/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    public ResponseEntity<?> mudarStatus(@PathVariable Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            CredencialReturnDTO credencial = credencialCrud.mudarStatus(id, requester);
            adicionarLinks.adicionarLink(credencial);
            return ResponseEntity.ok(credencial);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar credencial: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            credencialCrud.deletar(id, requester);
            return ResponseEntity.ok("Credencial deletada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao deletar credencial: " + e.getMessage());
        }
    }
}
