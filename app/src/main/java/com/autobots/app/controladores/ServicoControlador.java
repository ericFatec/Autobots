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
import com.autobots.app.modelos.servico.ServicoAdicionarLinks;
import com.autobots.app.services.EmpresaCrud;
import com.autobots.app.services.ServicoCrud;
import com.autobots.app.types.dtos.RequesterDTO;
import com.autobots.app.types.dtos.ServicoDTO;
import com.autobots.app.types.dtos.ServicoReturnDTO;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;



@RestController
@RequestMapping("/servico")
public class ServicoControlador {
    @Autowired
    private ServicoCrud servicoCrud;
    @Autowired
    private EmpresaCrud empresaCrud;

    @Autowired
    private ServicoAdicionarLinks adicionarLinks;

    @Autowired
    private RequesterBuilder requesterBuilder;

    @PostMapping("/{idEmpresa}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<?> adicionar(@PathVariable Long idEmpresa, @RequestBody ServicoDTO servicoDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            ServicoReturnDTO servico = empresaCrud.inserirServico(idEmpresa, servicoDTO, requester);
            adicionarLinks.adicionarLink(servico);
            return ResponseEntity.ok(servico);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao cadastrar serviço: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    public ResponseEntity<?> selecionar(@PathVariable Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            ServicoReturnDTO servico = servicoCrud.selecionar(id, requester);
            adicionarLinks.adicionarLink(servico);
            return ResponseEntity.ok(servico);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar serviço: " + e.getMessage());
        }
    }

    @GetMapping("/todos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> selecionarTodos() {
        try {
            List<ServicoReturnDTO> servicos = servicoCrud.selecionarTodos();
            adicionarLinks.adicionarLink(servicos);
            return ResponseEntity.ok(servicos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar serviços: " + e.getMessage());
        }
    }

    @GetMapping("/todos/{idEmpresa}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('VENDEDOR')")
    public ResponseEntity<?> selecionarFromEmpresa(@PathVariable Long idEmpresa) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            List<ServicoReturnDTO> servicos = servicoCrud.selecionarFromEmpresa(idEmpresa, requester);
            adicionarLinks.adicionarLink(servicos);
            return ResponseEntity.ok(servicos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar serviços da empresa: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody ServicoDTO novosDados) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            ServicoReturnDTO servico = servicoCrud.atualizar(id, novosDados, requester);
            adicionarLinks.adicionarLink(servico);
            return ResponseEntity.ok(servico);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao atualizar serviço: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            RequesterDTO requester = requesterBuilder.build(authentication);

            servicoCrud.deletar(id, requester);
            return ResponseEntity.ok("Serviço deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao deletar serviço: " + e.getMessage());
        }
    }
    
}
