package com.autobots.app.modelos.endereco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Endereco;
import com.autobots.app.repositorios.EnderecoRepositorio;
import com.autobots.app.types.dtos.EnderecoDTO;
import com.autobots.app.utils.mappers.EnderecoMapper;

import jakarta.transaction.Transactional;

@Component
public class EnderecoAtualizar {

    @Autowired
    private EnderecoRepositorio enderecoRepositorio;

    @Autowired
    private EnderecoMapper enderecoMapper;

    @Transactional
    public Endereco atualizar(Endereco endereco, EnderecoDTO novosDados) {
        enderecoMapper.updateEndereco(novosDados, endereco);
        return enderecoRepositorio.save(endereco);
    }
}
