package com.autobots.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.app.entidades.Endereco;
import com.autobots.app.modelos.endereco.EnderecoAtualizar;
import com.autobots.app.repositorios.EnderecoRepositorio;
import com.autobots.app.types.dtos.EnderecoDTO;
import com.autobots.app.types.dtos.EnderecoReturnDTO;
import com.autobots.app.types.interfaces.CrudInterface;
import com.autobots.app.utils.mappers.EnderecoMapper;

import jakarta.transaction.Transactional;

@Service
public class EnderecoCrud implements CrudInterface<EnderecoReturnDTO, Long, EnderecoDTO>{
    @Autowired
    private EnderecoRepositorio enderecoRepositorio;

    @Autowired
    private EnderecoAtualizar enderecoAtualizar;

    @Autowired
    private EnderecoMapper enderecoMapper;

    @Override
    @Transactional
    public EnderecoReturnDTO selecionar(Long id) {
        return enderecoMapper.toReturnDTO(enderecoRepositorio.findById(id).get());
    }

    @Override
    @Transactional
    public List<EnderecoReturnDTO> selecionarTodos() {
        return enderecoRepositorio.findAll()
            .stream()
            .map(enderecoMapper::toReturnDTO)
            .toList();
    }

    @Override
    @Transactional
    public EnderecoReturnDTO atualizar(Long id, EnderecoDTO novosDados) {
        Endereco enderecoAlvo = enderecoRepositorio.findById(id).get();
        return enderecoMapper.toReturnDTO(enderecoAtualizar.atualizar(enderecoAlvo, novosDados));
    }

    @Override
    public void deletar(Long id) {
        enderecoRepositorio.deleteById(id);
    }
}
