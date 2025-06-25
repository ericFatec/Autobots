package com.autobots.app.modelos.documento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Cliente;
import com.autobots.app.entidades.Documento;
import com.autobots.app.repositorios.cliente.ClienteRepositorio;
import com.autobots.app.services.DocumentoCrud;

import jakarta.transaction.Transactional;

@Component
public class ClienteInserirDocumento {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private DocumentoCrud documentoCrud;

    @Transactional
    public Documento inserirDocumento(Cliente cliente, Documento documento) {
        Documento documentoInserido = documentoCrud.inserir(documento);
        cliente.getDocumentos().add(documentoInserido);
        clienteRepositorio.save(cliente);
        return documentoInserido;
    }
}
