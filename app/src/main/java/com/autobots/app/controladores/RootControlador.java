package com.autobots.app.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.app.modelos.RootAdicionarLinks;

import lombok.Getter;

@RestController
@RequestMapping("/")
public class RootControlador {

    @Autowired
    private RootAdicionarLinks adicionarLinks;

    @GetMapping
    public ResponseEntity<ApiRoot> root() {
        ApiRoot root = new ApiRoot();

        adicionarLinks.adicionarLink(root);

        return ResponseEntity.ok(root);
    }

    @Getter
    public static class ApiRoot extends RepresentationModel<ApiRoot> {
        private final String message = "AutoBots API - Sistema de Gestão de Clientes";
        private final String description = "API RESTful para gerenciamento de clientes, documentos, endereços e telefones.";
    }
}
