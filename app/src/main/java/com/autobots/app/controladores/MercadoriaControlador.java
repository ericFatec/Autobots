package com.autobots.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.app.modelos.mercadoria.MercadoriaAdicionarLinks;
import com.autobots.app.services.EmpresaCrud;
import com.autobots.app.services.MercadoriaCrud;
import com.autobots.app.services.UsuarioCrud;
import com.autobots.app.types.dtos.MercadoriaDTO;
import com.autobots.app.types.dtos.MercadoriaEstoqueDTO;
import com.autobots.app.types.dtos.MercadoriaReturnDTO;

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

    @PostMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> adicionarNoUsuario(@PathVariable Long idUsuario, @Valid @RequestBody MercadoriaDTO mercadoriaDTO) {
        try {
            MercadoriaReturnDTO mercadoriaNova = usuarioCrud.inserirMercadoria(idUsuario, mercadoriaDTO);
            adicionarLinks.adicionarLink(mercadoriaNova);
            return ResponseEntity.ok(mercadoriaNova);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao cadastrar mercadoria: " + e.getMessage());
        }
    }
    
    @PostMapping("/empresa/{idEmpresa}")
    public ResponseEntity<?> adicionarNaEmpresa(@PathVariable Long idEmpresa, @Valid @RequestBody MercadoriaDTO mercadoriaDTO) {
        try {
            MercadoriaReturnDTO mercadoriaNova = empresaCrud.inserirMercadoria(idEmpresa, mercadoriaDTO);
            adicionarLinks.adicionarLink(mercadoriaNova);
            return ResponseEntity.ok(mercadoriaNova);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao cadastrar mercadoria: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> selecionar(@PathVariable Long id) {
        try {
            MercadoriaReturnDTO mercadoria = mercadoriaCrud.selecionar(id);
            adicionarLinks.adicionarLink(mercadoria);
            return ResponseEntity.ok(mercadoria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar mercadoria: " + e.getMessage());
        }
    }

    @GetMapping("/todos")
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
    public ResponseEntity<?> selecionarFromUsuario(@PathVariable Long idUsuario) {
        try {
            List<MercadoriaReturnDTO> mercadorias = mercadoriaCrud.selecionarFromUsuario(idUsuario);
            adicionarLinks.adicionarLink(mercadorias);
            return ResponseEntity.ok(mercadorias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar mercadorias do usuário: " + e.getMessage());
        }
    }

    @GetMapping("/empresa/todos/{idEmpresa}")
    public ResponseEntity<?> selecionarFromEmpresa(@PathVariable Long idEmpresa) {
        try {
            List<MercadoriaReturnDTO> mercadorias = mercadoriaCrud.selecionarFromEmpresa(idEmpresa);
            adicionarLinks.adicionarLink(mercadorias);
            return ResponseEntity.ok(mercadorias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar mercadorias da empresa: " + e.getMessage());
        }
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody MercadoriaDTO novosDados) {
        try {
            MercadoriaReturnDTO mercadoria = mercadoriaCrud.atualizar(id, novosDados);
            adicionarLinks.adicionarLink(mercadoria);
            return ResponseEntity.ok(mercadoria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao atualizar mercadoria: " + e.getMessage());
        }
    }

    @PatchMapping("/estoque/{id}")
    public ResponseEntity<?> atualizarEstoque(@PathVariable Long id, @RequestBody MercadoriaEstoqueDTO novosDados) {
        try {
            MercadoriaReturnDTO mercadoria = mercadoriaCrud.atualizarEstoque(id, novosDados);
            adicionarLinks.adicionarLink(mercadoria);
            return ResponseEntity.ok(mercadoria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao atualizar estoque da mercadoria: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            mercadoriaCrud.deletar(id);
            return ResponseEntity.ok("Mercadoria deletada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao deletar mercadoria: " + e.getMessage());
        }
    }
}
