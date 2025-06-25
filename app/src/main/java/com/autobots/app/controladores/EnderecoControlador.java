package com.autobots.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.app.modelos.RequesterBuilder;
import com.autobots.app.modelos.endereco.EnderecoAdicionarLinks;
import com.autobots.app.services.EnderecoCrud;
import com.autobots.app.types.dtos.EnderecoDTO;
import com.autobots.app.types.dtos.EnderecoReturnDTO;
import com.autobots.app.types.dtos.RequesterDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

@RestController
@RequestMapping("/endereco")
public class EnderecoControlador {
    @Autowired
    private EnderecoCrud enderecoCrud;

    @Autowired
    private EnderecoAdicionarLinks adicionarLinks;

    @Autowired
    private RequesterBuilder requesterBuilder;

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> selecionar(@PathVariable Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            EnderecoReturnDTO endereco = enderecoCrud.selecionar(id, requester);
            adicionarLinks.adicionarLink(endereco);
            return ResponseEntity.ok(endereco);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar endereço: " + e.getMessage());
        }
    }

    @GetMapping("/todos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> selecionarTodos() {
        try {
            List<EnderecoReturnDTO> enderecos = enderecoCrud.selecionarTodos();
            adicionarLinks.adicionarLink(enderecos);
            return ResponseEntity.ok(enderecos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar endereços: " + e.getMessage());
        }
    }
    
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody EnderecoDTO endereco) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            EnderecoReturnDTO enderecoAtualizado = enderecoCrud.atualizar(id, endereco, requester);
            return ResponseEntity.ok(enderecoAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao atualizar endereço: " + e.getMessage());
        }
    }
}
