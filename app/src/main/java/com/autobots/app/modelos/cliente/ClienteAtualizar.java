package com.autobots.app.modelos.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Cliente;
import com.autobots.app.repositorios.cliente.ClienteRepositorio;
import com.autobots.app.types.dtos.ClienteAtualizadorDTO;
import com.autobots.app.utils.StringVerificador;

import jakarta.annotation.PostConstruct;

@Component
public class ClienteAtualizar {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ClienteLoader clienteLoader;

    private StringVerificador stringVerificador;

    @PostConstruct
    public void init() {
        this.stringVerificador = new StringVerificador();
    }

    public Cliente atualizar(Cliente cliente, ClienteAtualizadorDTO novosDados) {
        if (
            novosDados.getNome() != null &&
            !novosDados.getNome().isEmpty() &&
            stringVerificador.verificar(novosDados.getNome())
        ) {
            cliente.setNome(novosDados.getNome());
        }
        if (
            novosDados.getNomeSocial() != null &&
            !novosDados.getNomeSocial().isEmpty() &&
            stringVerificador.verificar(novosDados.getNomeSocial())
        ) {
            cliente.setNomeSocial(novosDados.getNomeSocial());
        }
        if (
            novosDados.getDataNascimento() != null
        ) {
            cliente.setDataNascimento(novosDados.getDataNascimento());
        }
        
        return clienteLoader.loadFullCliente(clienteRepositorio.save(clienteRepositorio.save(cliente)));
    }
}
