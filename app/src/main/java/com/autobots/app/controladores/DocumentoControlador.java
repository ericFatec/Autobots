package com.autobots.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.app.modelos.RequesterBuilder;
import com.autobots.app.modelos.documento.DocumentoAdicionarLinks;
import com.autobots.app.services.UsuarioCrud;
import com.autobots.app.services.DocumentoCrud;
import com.autobots.app.types.dtos.DocumentoDTO;
import com.autobots.app.types.dtos.DocumentoReturnDTO;
import com.autobots.app.types.dtos.RequesterDTO;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;



@RestController
@RequestMapping("/documento")
public class DocumentoControlador {
    @Autowired
    private DocumentoCrud documentoCrud;
    @Autowired
    private UsuarioCrud usuarioCrud;

    @Autowired
    private DocumentoAdicionarLinks adicionarLinks;

    @Autowired
    private RequesterBuilder requesterBuilder;

    @PostMapping("/{idUsuario}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    public ResponseEntity<?> adicionar(@PathVariable Long idUsuario, @Valid @RequestBody DocumentoDTO documento) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            DocumentoReturnDTO documentoNovo = usuarioCrud.inserirDocumento(idUsuario, documento, requester);
            adicionarLinks.adicionarLink(documentoNovo);
            return ResponseEntity.ok(documentoNovo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao cadastrar documento: " + e.getMessage());
        }
    }

    @GetMapping("/todos/{idUsuario}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> selecionarFromUsuario(@PathVariable Long idUsuario) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            List<DocumentoReturnDTO> documentos = documentoCrud.selecionarFromUsuario(idUsuario, requester);
            adicionarLinks.adicionarLink(documentos);
            return ResponseEntity.ok(documentos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar documentos do usuario: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> selecionar(@PathVariable Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            DocumentoReturnDTO documento = documentoCrud.selecionar(id, requester);
            adicionarLinks.adicionarLink(documento);
            return ResponseEntity.ok(documento);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar documento: " + e.getMessage());
        }
    }

    @GetMapping("/todos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> selecionarTodos() {
        try {
            List<DocumentoReturnDTO> documentos = documentoCrud.selecionarTodos();
            adicionarLinks.adicionarLink(documentos);
            return ResponseEntity.ok(documentos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar documentos: " + e.getMessage());
        }
    }
    
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody DocumentoDTO documento) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            DocumentoReturnDTO documentoAtualizado = documentoCrud.atualizar(id, documento, requester);
            adicionarLinks.adicionarLink(documentoAtualizado);
            return ResponseEntity.ok(documentoAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao atualizar documento: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            documentoCrud.deletar(id, requester);
            return ResponseEntity.ok("Documento deletado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao deletar documento: " + e.getMessage());
        }
    }
}
