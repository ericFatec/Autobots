package com.autobots.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.app.modelos.empresa.EmpresaAdicionarLinks;
import com.autobots.app.services.EmpresaCrud;
import com.autobots.app.types.dtos.EmpresaCadastroDTO;
import com.autobots.app.types.dtos.EmpresaDTO;
import com.autobots.app.types.dtos.EmpresaReturnDTO;

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

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody EmpresaCadastroDTO empresa) {
        try {
            EmpresaReturnDTO empresaNova = empresaCrud.inserir(empresa);
            adicionarLinks.adicionarLink(empresaNova);
            return ResponseEntity.ok(empresaNova);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao cadastrar empresa: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> selecionar(@PathVariable Long id) {
        try {
            EmpresaReturnDTO empresa = empresaCrud.selecionar(id);
            adicionarLinks.adicionarLink(empresa);
            return ResponseEntity.ok(empresa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar empresa: " + e.getMessage());
        }
    }
    
    @GetMapping("/todos")
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
    public ResponseEntity<?> atualizar(@PathVariable Long id, EmpresaDTO novosDados) {
        try {
            EmpresaReturnDTO empresa = empresaCrud.atualizar(id, novosDados);
            adicionarLinks.adicionarLink(empresa);
            return ResponseEntity.ok(empresa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao atualizar empresa: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            empresaCrud.deletar(id);
            return ResponseEntity.ok("Empresa deletada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao deletar empresa: " + e.getMessage());
        }
    }
}
