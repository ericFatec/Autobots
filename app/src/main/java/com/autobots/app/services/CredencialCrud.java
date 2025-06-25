package com.autobots.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.app.entidades.Credencial;
import com.autobots.app.entidades.Usuario;
import com.autobots.app.modelos.credencial.CredencialAtualizar;
import com.autobots.app.modelos.credencial.CredencialRemover;
import com.autobots.app.repositorios.usuario.CredencialRepositorio;
import com.autobots.app.types.dtos.CredencialDTO;
import com.autobots.app.types.dtos.CredencialReturnDTO;
import com.autobots.app.types.dtos.RequesterDTO;
import com.autobots.app.types.interfaces.CrudInterface;
import com.autobots.app.utils.CrudPermissionChecker;
import com.autobots.app.utils.mappers.CredencialMapper;

import jakarta.transaction.Transactional;

@Service
public class CredencialCrud implements CrudInterface<CredencialReturnDTO, Long, CredencialDTO, RequesterDTO>{
    @Autowired
    private CredencialRepositorio credencialRepositorio;

    @Autowired
    private CredencialAtualizar credencialAtualizar;
    @Autowired
    private CredencialRemover credencialRemover;

    @Autowired
    private CredencialMapper credencialMapper;

    @Autowired
    private CrudPermissionChecker permissionCheck;

    @Override
    @Transactional
    public CredencialReturnDTO selecionar(Long id, RequesterDTO requester) {
        Credencial credencial = credencialRepositorio.findById(id).get();
        Usuario usuarioAlvo = credencial.getUsuario();

        permissionCheck.checkUserAccess(usuarioAlvo, requester);

        return credencialMapper.toReturnDTOPolymorphic(credencial);
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
    public List<CredencialReturnDTO> selecionarFromUsuario(Long id, RequesterDTO requester) {
        List<Credencial> credenciais = credencialRepositorio.findByUsuarioId(id);
        Usuario usuarioAlvo = credenciais.get(0).getUsuario();
        
        permissionCheck.checkUserAccess(usuarioAlvo, requester);

        return credenciais
            .stream()
            .map(credencialMapper::toReturnDTOPolymorphic)
            .toList();
    }

    @Override
    @Transactional
    public CredencialReturnDTO atualizar(Long id, CredencialDTO novosDados, RequesterDTO requester) {
        Credencial credencialAlvo = credencialRepositorio.findById(id).get();
        Usuario usuarioAlvo = credencialAlvo.getUsuario();
        
        permissionCheck.checkUserAccess(usuarioAlvo, requester);

        Credencial atualizada = credencialAtualizar.atualizar(credencialAlvo, novosDados);
        if (atualizada == null) {
            throw new IllegalStateException("Tipo de credencial inválida para atualização.");
        }
        return credencialMapper.toReturnDTOPolymorphic(atualizada);
    }

    @Transactional
    public CredencialReturnDTO mudarStatus(Long id, RequesterDTO requester) {
        Credencial credencialAlvo = credencialRepositorio.findById(id).get();
        Usuario usuarioAlvo = credencialAlvo.getUsuario();
        
        permissionCheck.checkUserAccess(usuarioAlvo, requester);

        credencialAlvo.setInativo(!credencialAlvo.isInativo());
        return credencialMapper.toReturnDTOPolymorphic(
            credencialRepositorio.save(credencialAlvo)
        );
    }

    @Override
    public void deletar(Long id, RequesterDTO requester) {
        Credencial credencialAlvo = credencialRepositorio.findById(id).get();
        Usuario usuarioAlvo = credencialAlvo.getUsuario();
        
        permissionCheck.checkUserAccess(usuarioAlvo, requester);

        credencialRemover.removerCredencial(credencialAlvo);
    }
}
