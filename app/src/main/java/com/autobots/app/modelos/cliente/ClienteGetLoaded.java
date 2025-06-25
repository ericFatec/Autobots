package com.autobots.app.modelos.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Cliente;

@Component
public class ClienteGetLoaded {

    @Autowired
    private ClienteLoader clienteLoader;

    public Cliente getOne(Cliente cliente) {
        return clienteLoader.loadFullCliente(cliente);
    }

    public List<Cliente> getAll(List<Cliente> clientes) {
        return clientes.stream()
            .map(clienteLoader::loadFullCliente)
            .toList();
    }
}
