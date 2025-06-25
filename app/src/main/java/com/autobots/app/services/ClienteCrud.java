package com.autobots.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.app.entidades.Cliente;
import com.autobots.app.entidades.Documento;
import com.autobots.app.entidades.Telefone;
import com.autobots.app.modelos.cliente.ClienteAtualizar;
import com.autobots.app.modelos.cliente.ClienteGetLoaded;
import com.autobots.app.modelos.cliente.ClienteInsercao;
import com.autobots.app.modelos.documento.ClienteInserirDocumento;
import com.autobots.app.modelos.documento.ClienteRemoverDocumento;
import com.autobots.app.modelos.telefone.ClienteInserirTelefone;
import com.autobots.app.modelos.telefone.ClienteRemoverTelefone;
import com.autobots.app.repositorios.cliente.ClienteRepositorio;
import com.autobots.app.types.dtos.ClienteAtualizadorDTO;
import com.autobots.app.types.interfaces.CrudInterface;

@Service
public class ClienteCrud implements CrudInterface<Cliente, Long, ClienteAtualizadorDTO> {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ClienteInsercao clienteInsercao;
    @Autowired
    private ClienteAtualizar clienteAtualizar;
    @Autowired
    private ClienteGetLoaded clienteGetLoaded;

    @Autowired
    private ClienteInserirDocumento clienteInserirDocumento;
    @Autowired
    private ClienteRemoverDocumento clienteRemoverDocumento;

    @Autowired
    private ClienteInserirTelefone clienteInserirTelefone;
    @Autowired
    private ClienteRemoverTelefone clienteRemoverTelefone;

    public Cliente inserir(Cliente cliente) {
        return clienteInsercao.inserir(cliente);
    }

    public Cliente selecionar(Long id) {
        return clienteGetLoaded.getOne(clienteRepositorio.findById(id).get());
    }

    public List<Cliente> selecionarTodos() {
        return clienteGetLoaded.getAll(clienteRepositorio.findAll());
    }

    public Cliente atualizar(Long id, ClienteAtualizadorDTO novosDados) {
        Cliente clienteAlvo = clienteRepositorio.findById(id).get();
        return clienteAtualizar.atualizar(clienteAlvo, novosDados);
    }

    public Documento inserirDocumento(Long id, Documento documento) {
        Cliente clienteAlvo = clienteRepositorio.findById(id).get();
        return clienteInserirDocumento.inserirDocumento(clienteAlvo, documento);
    }

    public void removerDocumento(Long idDocumento) {
        Cliente clienteAlvo = clienteRepositorio.findByDocumentoId(idDocumento);
        clienteRemoverDocumento.removerDocumento(clienteAlvo, idDocumento);
    }

    public Telefone inserirTelefone(Long id, Telefone telefone) {
        Cliente clienteAlvo = clienteRepositorio.findById(id).get();
        return clienteInserirTelefone.inserirTelefone(clienteAlvo, telefone);
    }
    
    public void removerTelefone(Long idTelefone) {
        Cliente clienteAlvo = clienteRepositorio.findByTelefoneId(idTelefone);
        clienteRemoverTelefone.removerTelefone(clienteAlvo, idTelefone);
    }

    public void deletar(Long id) {
        clienteRepositorio.deleteById(id);
    }
}
