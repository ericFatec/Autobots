package com.autobots.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.app.modelos.servico.ServicoAdicionarLinks;
import com.autobots.app.services.EmpresaCrud;
import com.autobots.app.services.ServicoCrud;
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

    @PostMapping("/{idEmpresa}")
    public ResponseEntity<?> adicionar(@PathVariable Long idEmpresa, @RequestBody ServicoDTO servicoDTO) {
        try {
            ServicoReturnDTO servico = empresaCrud.inserirServico(idEmpresa, servicoDTO);
            adicionarLinks.adicionarLink(servico);
            return ResponseEntity.ok(servico);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao cadastrar serviço: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> selecionar(@PathVariable Long id) {
        try {
            ServicoReturnDTO servico = servicoCrud.selecionar(id);
            adicionarLinks.adicionarLink(servico);
            return ResponseEntity.ok(servico);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar serviço: " + e.getMessage());
        }
    }

    @GetMapping("/todos")
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
    public ResponseEntity<?> selecionarFromEmpresa(@PathVariable Long idEmpresa) {
        try {
            List<ServicoReturnDTO> servicos = servicoCrud.selecionarFromEmpresa(idEmpresa);
            adicionarLinks.adicionarLink(servicos);
            return ResponseEntity.ok(servicos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar serviços da empresa: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody ServicoDTO novosDados) {
        try {
            ServicoReturnDTO servico = servicoCrud.atualizar(id, novosDados);
            adicionarLinks.adicionarLink(servico);
            return ResponseEntity.ok(servico);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao atualizar serviço: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            servicoCrud.deletar(id);
            return ResponseEntity.ok("Serviço deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao deletar serviço: " + e.getMessage());
        }
    }
    
}
