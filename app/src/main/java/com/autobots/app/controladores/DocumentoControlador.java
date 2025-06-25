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

import com.autobots.app.entidades.Documento;
import com.autobots.app.services.ClienteCrud;
import com.autobots.app.services.DocumentoCrud;
import com.autobots.app.types.dtos.DocumentoDTO;

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
    private ClienteCrud clienteCrud;

    @PostMapping("/{idCliente}")
    public ResponseEntity<?> adicionar(@PathVariable Long idCliente, @Valid @RequestBody Documento documento) {
        try {
            Documento documentoNovo = clienteCrud.inserirDocumento(idCliente, documento);
            return ResponseEntity.ok(documentoNovo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao cadastrar documento: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> selecionar(@PathVariable Long id) {
        try {
            Documento documento = documentoCrud.selecionar(id);
            return ResponseEntity.ok(documento);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar documento: " + e.getMessage());
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<?> selecionarTodos() {
        try {
            List<Documento> documentos = documentoCrud.selecionarTodos();
            return ResponseEntity.ok(documentos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar documentos: " + e.getMessage());
        }
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody DocumentoDTO documento) {
        try {
            Documento documentoAtualizado = documentoCrud.atualizar(id, documento);
            return ResponseEntity.ok(documentoAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao atualizar documento: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            clienteCrud.removerDocumento(id);
            return ResponseEntity.ok("Documento deletado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao deletar documento: " + e.getMessage());
        }
    }
}
