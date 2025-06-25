package com.autobots.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.app.modelos.veiculo.VeiculoAdicionarLinks;
import com.autobots.app.services.UsuarioCrud;
import com.autobots.app.services.VeiculoCrud;
import com.autobots.app.types.dtos.VeiculoDTO;
import com.autobots.app.types.dtos.VeiculoReturnDTO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/veiculo")
public class VeiculoControlador {
    @Autowired
    private VeiculoCrud veiculoCrud;
    @Autowired
    private UsuarioCrud usuarioCrud;

    @Autowired
    private VeiculoAdicionarLinks adicionarLinks;

    @PostMapping("/{idUsuario}")
    public ResponseEntity<?> adicionar(@PathVariable Long idUsuario, @RequestBody VeiculoDTO veiculo) {
        try {
            VeiculoReturnDTO veiculoNovo = usuarioCrud.inserirVeiculo(idUsuario, veiculo);
            adicionarLinks.adicionarLink(veiculoNovo);
            return ResponseEntity.ok(veiculoNovo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao cadastrar veiculo: " + e.getMessage());
        }
    }

    @GetMapping("/todos/{idUsuario}")
    public ResponseEntity<?> selecionarFromUsuario(@PathVariable Long idUsuario) {
        try {
            List<VeiculoReturnDTO> veiculos = veiculoCrud.selecionarFromUsuario(idUsuario);
            adicionarLinks.adicionarLink(veiculos);
            return ResponseEntity.ok(veiculos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar veiculos do usuário: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> selecionar(@PathVariable Long id) {
        try {
            VeiculoReturnDTO veiculo = veiculoCrud.selecionar(id);
            adicionarLinks.adicionarLink(veiculo);
            return ResponseEntity.ok(veiculo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar veiculo: " + e.getMessage());
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<?> selecionarTodos() {
        try {
            List<VeiculoReturnDTO> veiculos = veiculoCrud.selecionarTodos();
            adicionarLinks.adicionarLink(veiculos);
            return ResponseEntity.ok(veiculos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar veiculos: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody VeiculoDTO novosDados) {
        try {
            VeiculoReturnDTO veiculo = veiculoCrud.atualizar(id, novosDados);
            adicionarLinks.adicionarLink(veiculo);
            return ResponseEntity.ok(veiculo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao atualizar veiculo: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            veiculoCrud.deletar(id);
            return ResponseEntity.ok("Veículo deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao deletar veiculo: " + e.getMessage());
        }
    }
}
