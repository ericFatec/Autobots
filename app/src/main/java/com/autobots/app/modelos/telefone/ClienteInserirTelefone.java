package com.autobots.app.modelos.telefone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Cliente;
import com.autobots.app.entidades.Telefone;
import com.autobots.app.repositorios.cliente.ClienteRepositorio;
import com.autobots.app.services.TelefoneCrud;

import jakarta.transaction.Transactional;

@Component
public class ClienteInserirTelefone {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private TelefoneCrud telefoneCrud;

    @Transactional
    public Telefone inserirTelefone(Cliente cliente, Telefone telefone) {
        Telefone telefoneInserido = telefoneCrud.inserir(telefone);
        cliente.getTelefones().add(telefoneInserido);
        clienteRepositorio.save(cliente);
        return telefoneInserido;
    }
}
