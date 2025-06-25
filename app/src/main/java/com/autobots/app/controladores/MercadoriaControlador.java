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
import com.autobots.app.modelos.mercadoria.MercadoriaAdicionarLinks;
import com.autobots.app.services.EmpresaCrud;
import com.autobots.app.services.MercadoriaCrud;
import com.autobots.app.services.UsuarioCrud;
import com.autobots.app.types.dtos.MercadoriaDTO;
import com.autobots.app.types.dtos.MercadoriaEstoqueDTO;
import com.autobots.app.types.dtos.MercadoriaReturnDTO;
import com.autobots.app.types.dtos.RequesterDTO;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;




@RestController
@RequestMapping("/mercadoria")
public class MercadoriaControlador {
    @Autowired
    private MercadoriaCrud mercadoriaCrud;
    @Autowired
    private UsuarioCrud usuarioCrud;
    @Autowired
    private EmpresaCrud empresaCrud;

    @Autowired
    private MercadoriaAdicionarLinks adicionarLinks;

    @Autowired
    private RequesterBuilder requesterBuilder;

    @PostMapping("/usuario/{idUsuario}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    public ResponseEntity<?> adicionarNoUsuario(@PathVariable Long idUsuario, @Valid @RequestBody MercadoriaDTO mercadoriaDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            MercadoriaReturnDTO mercadoriaNova = usuarioCrud.inserirMercadoria(idUsuario, mercadoriaDTO, requester);
            adicionarLinks.adicionarLink(mercadoriaNova);
            return ResponseEntity.ok(mercadoriaNova);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao cadastrar mercadoria: " + e.getMessage());
        }
    }
    
    @PostMapping("/empresa/{idEmpresa}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<?> adicionarNaEmpresa(@PathVariable Long idEmpresa, @Valid @RequestBody MercadoriaDTO mercadoriaDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            MercadoriaReturnDTO mercadoriaNova = empresaCrud.inserirMercadoria(idEmpresa, mercadoriaDTO, requester);
            adicionarLinks.adicionarLink(mercadoriaNova);
            return ResponseEntity.ok(mercadoriaNova);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao cadastrar mercadoria: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    public ResponseEntity<?> selecionar(@PathVariable Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            MercadoriaReturnDTO mercadoria = mercadoriaCrud.selecionar(id, requester);
            adicionarLinks.adicionarLink(mercadoria);
            return ResponseEntity.ok(mercadoria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar mercadoria: " + e.getMessage());
        }
    }

    @GetMapping("/todos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> selecionarTodos() {
        try {
            List<MercadoriaReturnDTO> mercadorias = mercadoriaCrud.selecionarTodos();
            adicionarLinks.adicionarLink(mercadorias);
            return ResponseEntity.ok(mercadorias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar mercadorias: " + e.getMessage());
        }
    }

    @GetMapping("/usuario/todos/{idUsuario}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    public ResponseEntity<?> selecionarFromUsuario(@PathVariable Long idUsuario) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            List<MercadoriaReturnDTO> mercadorias = mercadoriaCrud.selecionarFromUsuario(idUsuario, requester);
            adicionarLinks.adicionarLink(mercadorias);
            return ResponseEntity.ok(mercadorias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar mercadorias do usuário: " + e.getMessage());
        }
    }

    @GetMapping("/empresa/todos/{idEmpresa}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    public ResponseEntity<?> selecionarFromEmpresa(@PathVariable Long idEmpresa) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            List<MercadoriaReturnDTO> mercadorias = mercadoriaCrud.selecionarFromEmpresa(idEmpresa, requester);
            adicionarLinks.adicionarLink(mercadorias);
            return ResponseEntity.ok(mercadorias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar mercadorias da empresa: " + e.getMessage());
        }
    }
    
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody MercadoriaDTO novosDados) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            MercadoriaReturnDTO mercadoria = mercadoriaCrud.atualizar(id, novosDados, requester);
            adicionarLinks.adicionarLink(mercadoria);
            return ResponseEntity.ok(mercadoria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao atualizar mercadoria: " + e.getMessage());
        }
    }

    @PatchMapping("/estoque/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<?> atualizarEstoque(@PathVariable Long id, @RequestBody MercadoriaEstoqueDTO novosDados) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            MercadoriaReturnDTO mercadoria = mercadoriaCrud.atualizarEstoque(id, novosDados, requester);
            adicionarLinks.adicionarLink(mercadoria);
            return ResponseEntity.ok(mercadoria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao atualizar estoque da mercadoria: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            mercadoriaCrud.deletar(id, requester);
            return ResponseEntity.ok("Mercadoria deletada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao deletar mercadoria: " + e.getMessage());
        }
    }
}
