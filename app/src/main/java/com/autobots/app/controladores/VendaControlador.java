package com.autobots.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.app.modelos.venda.VendaAdicionarLinks;
import com.autobots.app.services.VendaCrud;
import com.autobots.app.types.dtos.VendaDTO;
import com.autobots.app.types.dtos.VendaReturnDTO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/vendas")
public class VendaControlador {
    @Autowired
    private VendaCrud vendaCrud;

    @Autowired
    private VendaAdicionarLinks adicionarLinks;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> adicionar(@RequestBody VendaDTO novaVenda) {
        try {
            VendaReturnDTO venda = vendaCrud.inserir(novaVenda);
            adicionarLinks.adicionarLink(venda);
            return ResponseEntity.ok(venda);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao cadastrar venda: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> selecionar(@PathVariable Long id) {
        try {
            VendaReturnDTO venda = vendaCrud.selecionar(id);
            adicionarLinks.adicionarLink(venda);
            return ResponseEntity.ok(venda);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar venda: " + e.getMessage());
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<?> selecionarTodos() {
        try {
            List<VendaReturnDTO> vendas = vendaCrud.selecionarTodos();
            adicionarLinks.adicionarLink(vendas);
            return ResponseEntity.ok(vendas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar vendas: " + e.getMessage());
        }
    }

    @GetMapping("/usuario/todos/{idCliente}")
    public ResponseEntity<?> selecionarFromUsuario(@PathVariable Long idCliente) {
        try {
            List<VendaReturnDTO> vendas = vendaCrud.selecionarFromUsuario(idCliente);
            adicionarLinks.adicionarLink(vendas);
            return ResponseEntity.ok(vendas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar vendas do usuário: " + e.getMessage());
        }
    }

    @GetMapping("/empresa/todos/{idEmpresa}")
    public ResponseEntity<?> selecionarFromEmpresa(@PathVariable Long idEmpresa) {
        try {
            List<VendaReturnDTO> vendas = vendaCrud.selecionarFromUsuario(idEmpresa);
            adicionarLinks.adicionarLink(vendas);
            return ResponseEntity.ok(vendas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar vendas da empresa: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            vendaCrud.deletar(id);
            return ResponseEntity.ok("Venda deletada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao deletar venda: " + e.getMessage());
        }
    }
}
