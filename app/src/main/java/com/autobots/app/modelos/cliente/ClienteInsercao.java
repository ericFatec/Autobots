package com.autobots.app.modelos.cliente;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Cliente;
import com.autobots.app.entidades.Documento;
import com.autobots.app.entidades.Endereco;
import com.autobots.app.entidades.Telefone;
import com.autobots.app.repositorios.cliente.ClienteRepositorio;
import com.autobots.app.services.DocumentoCrud;
import com.autobots.app.services.EnderecoCrud;
import com.autobots.app.services.TelefoneCrud;

import jakarta.transaction.Transactional;

@Component
public class ClienteInsercao {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private DocumentoCrud documentoCrud;
    @Autowired
    private EnderecoCrud enderecoCrud;
    @Autowired
    private TelefoneCrud telefoneCrud;

    @Autowired
    private ClienteLoader clienteLoader;

    @Transactional
    public Cliente inserir(Cliente cliente) {
        List<Documento> documentos = cliente.getDocumentos();
        Endereco endereco = cliente.getEndereco();
        List<Telefone> telefones = cliente.getTelefones();

        cliente.setDocumentos(new ArrayList<>());
        cliente.setEndereco(null);
        cliente.setTelefones(new ArrayList<>());
        cliente.setDataCadastro(Instant.now());
        
        if (documentos != null && !documentos.isEmpty()) {
            for (Documento doc : documentos) {
                cliente.getDocumentos().add(documentoCrud.inserir(doc));
            }
        }
        if (endereco != null) {
            cliente.setEndereco(enderecoCrud.inserir(endereco));
        }
        if (telefones != null && !telefones.isEmpty()) {
            for (Telefone tel : telefones) {
                cliente.getTelefones().add(telefoneCrud.inserir(tel));
            }
        }

        return clienteLoader.loadFullCliente(clienteRepositorio.save(cliente));
    }
}
