package com.autobots.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.app.entidades.Credencial;
import com.autobots.app.modelos.credencial.CredencialAtualizar;
import com.autobots.app.modelos.credencial.CredencialRemover;
import com.autobots.app.repositorios.usuario.CredencialRepositorio;
import com.autobots.app.types.dtos.CredencialDTO;
import com.autobots.app.types.dtos.CredencialReturnDTO;
import com.autobots.app.types.interfaces.CrudInterface;
import com.autobots.app.utils.mappers.CredencialMapper;

import jakarta.transaction.Transactional;

@Service
public class CredencialCrud implements CrudInterface<CredencialReturnDTO, Long, CredencialDTO>{
    @Autowired
    private CredencialRepositorio credencialRepositorio;

    @Autowired
    private CredencialAtualizar credencialAtualizar;
    @Autowired
    private CredencialRemover credencialRemover;

    @Autowired
    private CredencialMapper credencialMapper;

    @Override
    @Transactional
    public CredencialReturnDTO selecionar(Long id) {
        return credencialMapper.toReturnDTOPolymorphic(
            credencialRepositorio.findById(id).get()
        );
    }

    @Override
    @Transactional
    public List<CredencialReturnDTO> selecionarTodos() {
        return credencialRepositorio.findAll()
            .stream()
            .map(credencialMapper::toReturnDTOPolymorphic)
            .toList();
    }

    @Transactional
    public List<CredencialReturnDTO> selecionarFromUsuario(Long id) {
        return credencialRepositorio.findByUsuarioId(id)
            .stream()
            .map(credencialMapper::toReturnDTOPolymorphic)
            .toList();
    }

    @Override
    @Transactional
    public CredencialReturnDTO atualizar(Long id, CredencialDTO novosDados) {
        Credencial credencialAlvo = credencialRepositorio.findById(id).get();
        Credencial atualizada = credencialAtualizar.atualizar(credencialAlvo, novosDados);
        if (atualizada == null) {
            throw new IllegalStateException("Tipo de credencial inválida para atualização.");
        }
        return credencialMapper.toReturnDTOPolymorphic(atualizada);
    }

    @Transactional
    public CredencialReturnDTO mudarStatus(Long id) {
        Credencial credencialAlvo = credencialRepositorio.findById(id).get();
        credencialAlvo.setInativo(!credencialAlvo.isInativo());
        return credencialMapper.toReturnDTOPolymorphic(
            credencialRepositorio.save(credencialAlvo)
        );
    }

    @Override
    public void deletar(Long id) {
        credencialRemover.removerCredencial(id);
    }
}
