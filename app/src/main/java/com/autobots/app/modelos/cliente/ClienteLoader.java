package com.autobots.app.modelos.cliente;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Cliente;
import com.autobots.app.entidades.Endereco;

@Component
public class ClienteLoader {
    public Cliente loadFullCliente(Cliente cliente) {
        Hibernate.initialize(cliente.getDocumentos());
        Hibernate.initialize(cliente.getTelefones());
        Hibernate.initialize(cliente.getEndereco());

        cliente.setEndereco((Endereco) Hibernate.unproxy(cliente.getEndereco()));

        return cliente;
    }
}
