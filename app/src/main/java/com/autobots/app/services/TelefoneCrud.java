package com.autobots.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.app.entidades.Telefone;
import com.autobots.app.modelos.telefone.TelefoneAtualizar;
import com.autobots.app.modelos.telefone.TelefoneRemover;
import com.autobots.app.repositorios.TelefoneRepositorio;
import com.autobots.app.types.dtos.TelefoneDTO;
import com.autobots.app.types.dtos.TelefoneReturnDTO;
import com.autobots.app.types.interfaces.CrudInterface;
import com.autobots.app.utils.mappers.TelefoneMapper;

import jakarta.transaction.Transactional;

@Service
public class TelefoneCrud implements CrudInterface<TelefoneReturnDTO, Long, TelefoneDTO>{
    @Autowired
    private TelefoneRepositorio telefoneRepositorio;

    @Autowired
    private TelefoneAtualizar telefoneAtualizar;
    @Autowired
    private TelefoneRemover telefoneRemover;

    @Autowired
    private TelefoneMapper telefoneMapper;

    @Override
    @Transactional
    public TelefoneReturnDTO selecionar(Long id) {
        return telefoneMapper.toReturnDTO(telefoneRepositorio.findById(id).get());
    }

    @Override
    @Transactional
    public List<TelefoneReturnDTO> selecionarTodos() {
        return telefoneRepositorio.findAll()
            .stream()
            .map(telefoneMapper::toReturnDTO)
            .toList();
    }

    @Transactional
    public List<TelefoneReturnDTO> selecionarFromUsuario(Long id) {
        return telefoneRepositorio.findByUsuarioId(id)
            .stream()
            .map(telefoneMapper::toReturnDTO)
            .toList();
    }

    @Transactional
    public List<TelefoneReturnDTO> selecionarFromEmpresa(Long id) {
        return telefoneRepositorio.findByEmpresaId(id)
            .stream()
            .map(telefoneMapper::toReturnDTO)
            .toList();
    }

    @Override
    @Transactional
    public TelefoneReturnDTO atualizar(Long id, TelefoneDTO novosDados) {
        Telefone telefoneAlvo = telefoneRepositorio.findById(id).get();
        Telefone atualizado = telefoneAtualizar.atualizar(telefoneAlvo, novosDados);
        return telefoneMapper.toReturnDTO(atualizado);
    }

    @Override
    public void deletar(Long id) {
        telefoneRemover.removerTelefone(id);
    }
}
