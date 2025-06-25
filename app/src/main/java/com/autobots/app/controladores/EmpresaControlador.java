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
import com.autobots.app.modelos.empresa.EmpresaAdicionarLinks;
import com.autobots.app.services.EmpresaCrud;
import com.autobots.app.types.dtos.EmpresaCadastroDTO;
import com.autobots.app.types.dtos.EmpresaDTO;
import com.autobots.app.types.dtos.EmpresaReturnDTO;
import com.autobots.app.types.dtos.RequesterDTO;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/empresa")
public class EmpresaControlador {

    @Autowired
    private EmpresaCrud empresaCrud;

    @Autowired
    private EmpresaAdicionarLinks adicionarLinks;

    @Autowired
    private RequesterBuilder requesterBuilder;

    @PostMapping("/cadastrar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody EmpresaCadastroDTO empresa) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            EmpresaReturnDTO empresaNova = empresaCrud.inserir(empresa, requester);
            adicionarLinks.adicionarLink(empresaNova);
            return ResponseEntity.ok(empresaNova);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao cadastrar empresa: " + e.getMessage());
        }
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    public ResponseEntity<?> selecionarMe() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            if (requester.getUsuario().getEmpresa() == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Usuário não está vinculado a nenhuma empresa.");
            }

            EmpresaReturnDTO empresa = empresaCrud.selecionar(requester.getUsuario().getEmpresaId(), requester);
            adicionarLinks.adicionarLink(empresa);
            return ResponseEntity.ok(empresa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar empresa: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    public ResponseEntity<?> selecionar(@PathVariable Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            EmpresaReturnDTO empresa = empresaCrud.selecionar(id, requester);
            adicionarLinks.adicionarLink(empresa);
            return ResponseEntity.ok(empresa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar empresa: " + e.getMessage());
        }
    }
    
    @GetMapping("/todos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> selecionarTodos() {
        try {
            List<EmpresaReturnDTO> empresas = empresaCrud.selecionarTodos();
            adicionarLinks.adicionarLink(empresas);
            return ResponseEntity.ok(empresas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar empresas: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<?> atualizar(@PathVariable Long id, EmpresaDTO novosDados) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            EmpresaReturnDTO empresa = empresaCrud.atualizar(id, novosDados, requester);
            adicionarLinks.adicionarLink(empresa);
            return ResponseEntity.ok(empresa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao atualizar empresa: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            empresaCrud.deletar(id, requester);
            return ResponseEntity.ok("Empresa deletada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao deletar empresa: " + e.getMessage());
        }
    }
}
