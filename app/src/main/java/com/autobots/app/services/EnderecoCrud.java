package com.autobots.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.app.entidades.Endereco;
import com.autobots.app.modelos.endereco.EnderecoAtualizar;
import com.autobots.app.repositorios.endereco.EnderecoRepositorio;
import com.autobots.app.types.dtos.EnderecoDTO;
import com.autobots.app.types.interfaces.CrudInterface;

@Service
public class EnderecoCrud implements CrudInterface<Endereco, Long, EnderecoDTO>{
    @Autowired
    private EnderecoRepositorio enderecoRepositorio;

    @Autowired
    private EnderecoAtualizar enderecoAtualizar;

    public Endereco inserir(Endereco endereco) {
        return enderecoRepositorio.save(endereco);
    }

    public Endereco selecionar(Long id) {
        return enderecoRepositorio.findById(id).get();
    }

    public List<Endereco> selecionarTodos() {
        return enderecoRepositorio.findAll();
    }

    public Endereco atualizar(Long id, EnderecoDTO novosDados) {
        Endereco enderecoAlvo = enderecoRepositorio.findById(id).get();
        return enderecoAtualizar.atualizar(enderecoAlvo, novosDados);
    }

    public void deletar(Long id) {
        enderecoRepositorio.deleteById(id);
    }
}
