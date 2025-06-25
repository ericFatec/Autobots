package com.autobots.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.app.entidades.Endereco;
import com.autobots.app.modelos.endereco.EnderecoAdicionarLinks;
import com.autobots.app.services.EnderecoCrud;
import com.autobots.app.types.dtos.EnderecoDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

@RestController
@RequestMapping("/endereco")
public class EnderecoControlador {
    @Autowired
    private EnderecoCrud enderecoCrud;

    @Autowired
    private EnderecoAdicionarLinks adicionarLinks;

    @GetMapping("/{id}")
    public ResponseEntity<?> selecionar(@PathVariable Long id) {
        try {
            Endereco endereco = enderecoCrud.selecionar(id);
            adicionarLinks.adicionarLink(endereco);
            return ResponseEntity.ok(endereco);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar endereço: " + e.getMessage());
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<?> selecionarTodos() {
        try {
            List<Endereco> enderecos = enderecoCrud.selecionarTodos();
            adicionarLinks.adicionarLink(enderecos);
            return ResponseEntity.ok(enderecos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar endereços: " + e.getMessage());
        }
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody EnderecoDTO endereco) {
        try {
            Endereco enderecoAtualizado = enderecoCrud.atualizar(id, endereco);
            return ResponseEntity.ok(enderecoAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao atualizar endereço: " + e.getMessage());
        }
    }
}
