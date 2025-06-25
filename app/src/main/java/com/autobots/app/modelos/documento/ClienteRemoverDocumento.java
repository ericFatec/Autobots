package com.autobots.app.modelos.documento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Cliente;
import com.autobots.app.repositorios.cliente.ClienteRepositorio;
import com.autobots.app.services.DocumentoCrud;

import jakarta.transaction.Transactional;

@Component
public class ClienteRemoverDocumento {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private DocumentoCrud documentoCrud;

    @Transactional
    public void removerDocumento(Cliente cliente, Long idDocumento) {
        documentoCrud.deletar(idDocumento);
        cliente.getDocumentos().removeIf(doc -> doc.getId().equals(idDocumento));
        clienteRepositorio.save(cliente);
    }
}
