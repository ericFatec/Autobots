package com.autobots.app.modelos.telefone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Cliente;
import com.autobots.app.repositorios.cliente.ClienteRepositorio;
import com.autobots.app.services.TelefoneCrud;

import jakarta.transaction.Transactional;

@Component
public class ClienteRemoverTelefone {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private TelefoneCrud telefoneCrud;

    @Transactional
    public void removerTelefone(Cliente cliente, Long id) {
        telefoneCrud.deletar(id);
        cliente.getTelefones().removeIf(tel -> tel.getId().equals(id));
        clienteRepositorio.save(cliente);
    }
}
