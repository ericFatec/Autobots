package com.autobots.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.app.modelos.RequesterBuilder;
import com.autobots.app.modelos.usuario.UsuarioAdicionarLinks;
import com.autobots.app.services.EmpresaCrud;
import com.autobots.app.services.UsuarioCrud;
import com.autobots.app.types.dtos.RequesterDTO;
import com.autobots.app.types.dtos.UsuarioAtualizarDTO;
import com.autobots.app.types.dtos.UsuarioCadastroDTO;
import com.autobots.app.types.dtos.UsuarioReturnDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuarioControlador {
    @Autowired
    private UsuarioCrud usuarioCrud;
    @Autowired
    private EmpresaCrud empresaCrud;

    @Autowired
    private UsuarioAdicionarLinks adicionarLinks;

    @Autowired
    private RequesterBuilder requesterBuilder;

    @PostMapping("/cadastrar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody UsuarioCadastroDTO usuario) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            UsuarioReturnDTO usuarioNovo = usuarioCrud.inserir(usuario, requester);
            adicionarLinks.adicionarLink(usuarioNovo);
            return ResponseEntity.ok(usuarioNovo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao cadastrar usuario: " + e.getMessage());
        }
    }

    @PostMapping("empresa/cadastrar/{idEmpresa}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    public ResponseEntity<?> cadastrarNaEmpresa(@PathVariable Long idEmpresa, @Valid @RequestBody UsuarioCadastroDTO usuario) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            UsuarioReturnDTO usuarioNovo = empresaCrud.inserirUsuario(idEmpresa, usuario, requester);
            adicionarLinks.adicionarLink(usuarioNovo);
            return ResponseEntity.ok(usuarioNovo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao cadastrar usuario na empresa: " + e.getMessage());
        }
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> selecionarMe() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);
            
            UsuarioReturnDTO usuario = usuarioCrud.selecionar(requester.getUsuario().getId(), requester);
            adicionarLinks.adicionarLink(usuario);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar usuario: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    public ResponseEntity<?> selecionar(@PathVariable Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            UsuarioReturnDTO usuario = usuarioCrud.selecionar(id, requester);
            adicionarLinks.adicionarLink(usuario);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar usuario: " + e.getMessage());
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<?> selecionarTodos() {
        try {
            List<UsuarioReturnDTO> usuarios = usuarioCrud.selecionarTodos();
            adicionarLinks.adicionarLink(usuarios);
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar usuarios: " + e.getMessage());
        }
    }

    @GetMapping("empresa/todos/{idEmpresa}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<?> selecionarFromEmpresa(@PathVariable Long idEmpresa) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            List<UsuarioReturnDTO> usuarios = usuarioCrud.selecionarFromEmpresa(idEmpresa, requester);
            adicionarLinks.adicionarLink(usuarios);
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar usuarios da empresa: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody UsuarioAtualizarDTO novosDados) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            UsuarioReturnDTO usuario = usuarioCrud.atualizar(id, novosDados, requester);
            adicionarLinks.adicionarLink(usuario);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao atualizar usuario: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            usuarioCrud.deletar(id, requester);
            return ResponseEntity.ok("usuario deletado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao deletar usuario: " + e.getMessage());
        }
    }
}
