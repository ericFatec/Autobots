package com.autobots.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.entidades.Telefone;
import com.autobots.app.entidades.Usuario;
import com.autobots.app.modelos.telefone.TelefoneAtualizar;
import com.autobots.app.modelos.telefone.TelefoneRemover;
import com.autobots.app.repositorios.TelefoneRepositorio;
import com.autobots.app.types.dtos.RequesterDTO;
import com.autobots.app.types.dtos.TelefoneDTO;
import com.autobots.app.types.dtos.TelefoneReturnDTO;
import com.autobots.app.types.enums.TipoPerfil;
import com.autobots.app.types.interfaces.CrudInterface;
import com.autobots.app.utils.CrudPermissionChecker;
import com.autobots.app.utils.mappers.TelefoneMapper;

import jakarta.transaction.Transactional;

@Service
public class TelefoneCrud implements CrudInterface<TelefoneReturnDTO, Long, TelefoneDTO, RequesterDTO>{
    @Autowired
    private TelefoneRepositorio telefoneRepositorio;

    @Autowired
    private TelefoneAtualizar telefoneAtualizar;
    @Autowired
    private TelefoneRemover telefoneRemover;

    @Autowired
    private TelefoneMapper telefoneMapper;

    @Autowired
    private CrudPermissionChecker permissionChecker;

    @Override
    @Transactional
    public TelefoneReturnDTO selecionar(Long id, RequesterDTO requester) {
        Telefone telefone = telefoneRepositorio.findById(id).get();
        if (telefone.getUsuario() != null) {
            Usuario usuarioAlvo = telefone.getUsuario();
            permissionChecker.checkUserAccess(usuarioAlvo, requester);
        } else {
            Empresa empresaAlvo = telefone.getEmpresa();
            permissionChecker.isFromEmpresa(empresaAlvo, requester);
        }
        return telefoneMapper.toReturnDTO(telefone);
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
    public List<TelefoneReturnDTO> selecionarFromUsuario(Long id, RequesterDTO requester) {
        List<Telefone> telefones = telefoneRepositorio.findByUsuarioId(id);
        Usuario usuarioAlvo = telefones.get(0).getUsuario();

        permissionChecker.checkUserAccess(usuarioAlvo, requester);

        return telefones
            .stream()
            .map(telefoneMapper::toReturnDTO)
            .toList();
    }

    @Transactional
    public List<TelefoneReturnDTO> selecionarFromEmpresa(Long id, RequesterDTO requester) {
        List<Telefone> telefones = telefoneRepositorio.findByEmpresaId(id);
        Empresa empresaAlvo = telefones.get(0).getEmpresa();

        permissionChecker.isFromEmpresa(empresaAlvo, requester);;

        return telefones
            .stream()
            .map(telefoneMapper::toReturnDTO)
            .toList();
    }

    @Override
    @Transactional
    public TelefoneReturnDTO atualizar(Long id, TelefoneDTO novosDados, RequesterDTO requester) {
        Telefone telefoneAlvo = telefoneRepositorio.findById(id).get();
        if (telefoneAlvo.getUsuario() != null) {
            Usuario usuarioAlvo = telefoneAlvo.getUsuario();
            permissionChecker.checkUserAccess(usuarioAlvo, requester);
        } else if (requester.getRole().equals(TipoPerfil.ADMIN) || requester.getRole().equals(TipoPerfil.GERENTE)) {
            Empresa empresaAlvo = telefoneAlvo.getEmpresa();
            permissionChecker.isFromEmpresa(empresaAlvo, requester);
        } else {
            throw new AccessDeniedException("Perfil sem permissão");
        }
        Telefone atualizado = telefoneAtualizar.atualizar(telefoneAlvo, novosDados);
        return telefoneMapper.toReturnDTO(atualizado);
    }

    @Override
    public void deletar(Long id, RequesterDTO requester) {
        Telefone telefoneAlvo = telefoneRepositorio.findById(id).get();
        if (telefoneAlvo.getUsuario() != null) {
            Usuario usuarioAlvo = telefoneAlvo.getUsuario();
            permissionChecker.checkUserAccess(usuarioAlvo, requester);
        } else if (requester.getRole().equals(TipoPerfil.ADMIN) || requester.getRole().equals(TipoPerfil.GERENTE)) {
            Empresa empresaAlvo = telefoneAlvo.getEmpresa();
            permissionChecker.isFromEmpresa(empresaAlvo, requester);
        } else {
            throw new AccessDeniedException("Perfil sem permissão");
        }
        telefoneRemover.removerTelefone(telefoneAlvo);
    }
}
