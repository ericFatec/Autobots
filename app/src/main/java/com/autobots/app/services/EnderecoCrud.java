package com.autobots.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.entidades.Endereco;
import com.autobots.app.entidades.Usuario;
import com.autobots.app.modelos.endereco.EnderecoAtualizar;
import com.autobots.app.repositorios.EmpresaRepositorio;
import com.autobots.app.repositorios.EnderecoRepositorio;
import com.autobots.app.repositorios.usuario.UsuarioRepositorio;
import com.autobots.app.types.dtos.EnderecoDTO;
import com.autobots.app.types.dtos.EnderecoReturnDTO;
import com.autobots.app.types.dtos.RequesterDTO;
import com.autobots.app.types.enums.TipoPerfil;
import com.autobots.app.types.interfaces.CrudInterface;
import com.autobots.app.utils.CrudPermissionChecker;
import com.autobots.app.utils.mappers.EnderecoMapper;

import jakarta.transaction.Transactional;

@Service
public class EnderecoCrud implements CrudInterface<EnderecoReturnDTO, Long, EnderecoDTO, RequesterDTO>{
    @Autowired
    private EnderecoRepositorio enderecoRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @Autowired
    private EnderecoAtualizar enderecoAtualizar;

    @Autowired
    private EnderecoMapper enderecoMapper;

    @Autowired
    private CrudPermissionChecker permissionChecker;

    @Override
    @Transactional
    public EnderecoReturnDTO selecionar(Long id, RequesterDTO requester) {
        Endereco endereco = enderecoRepositorio.findById(id).get();
        if (endereco.getUsuarioId() != null) {
            Usuario usuarioAlvo = usuarioRepositorio.findById(endereco.getUsuarioId()).get();
            permissionChecker.checkUserAccess(usuarioAlvo, requester);
        } else {
            Empresa empresaAlvo = empresaRepositorio.findById(endereco.getEmpresaId()).get();
            permissionChecker.isFromEmpresa(empresaAlvo, requester);
        }
        return enderecoMapper.toReturnDTO(endereco);
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
    public EnderecoReturnDTO atualizar(Long id, EnderecoDTO novosDados, RequesterDTO requester) {
        Endereco enderecoAlvo = enderecoRepositorio.findById(id).get();
        if (enderecoAlvo.getUsuarioId() != null) {
            Usuario usuarioAlvo = usuarioRepositorio.findById(enderecoAlvo.getUsuarioId()).get();
            permissionChecker.checkUserAccess(usuarioAlvo, requester);
        } else if (requester.getRole().equals(TipoPerfil.ADMIN) || requester.getRole().equals(TipoPerfil.GERENTE)) {
            Empresa empresaAlvo = empresaRepositorio.findById(enderecoAlvo.getEmpresaId()).get();
            permissionChecker.isFromEmpresa(empresaAlvo, requester);
        } else {
            throw new AccessDeniedException("Perfil sem permissão");
        }
        return enderecoMapper.toReturnDTO(enderecoAtualizar.atualizar(enderecoAlvo, novosDados));
    }

    @Override
    public void deletar(Long id, RequesterDTO requester) {
        enderecoRepositorio.deleteById(id);
    }
}
