package com.autobots.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.entidades.Mercadoria;
import com.autobots.app.entidades.Usuario;
import com.autobots.app.modelos.mercadoria.MercadoriaAtualizar;
import com.autobots.app.modelos.mercadoria.MercadoriaAtualizarEstoque;
import com.autobots.app.modelos.mercadoria.MercadoriaLoader;
import com.autobots.app.modelos.mercadoria.MercadoriaRemover;
import com.autobots.app.repositorios.mercadoria.MercadoriaRepositorio;
import com.autobots.app.types.dtos.MercadoriaDTO;
import com.autobots.app.types.dtos.MercadoriaEstoqueDTO;
import com.autobots.app.types.dtos.MercadoriaReturnDTO;
import com.autobots.app.types.dtos.RequesterDTO;
import com.autobots.app.types.interfaces.CrudInterface;
import com.autobots.app.utils.CrudPermissionChecker;
import com.autobots.app.utils.mappers.MercadoriaMapper;

import jakarta.transaction.Transactional;

@Service
public class MercadoriaCrud implements CrudInterface<MercadoriaReturnDTO, Long, MercadoriaDTO, RequesterDTO>{
    @Autowired
    private MercadoriaRepositorio mercadoriaRepositorio;

    @Autowired
    private MercadoriaLoader mercadoriaLoader;
    @Autowired
    private MercadoriaAtualizar mercadoriaAtualizar;
    @Autowired
    private MercadoriaRemover mercadoriaRemover;
    @Autowired
    private MercadoriaAtualizarEstoque mercadoriaAtualizarEstoque;

    @Autowired
    private MercadoriaMapper mercadoriaMapper;

    @Autowired
    private CrudPermissionChecker permissionChecker;
    
    @Override
    @Transactional
    public MercadoriaReturnDTO selecionar(Long id, RequesterDTO requester) {
        Mercadoria mercadoria = mercadoriaRepositorio.findById(id).get();
        if (mercadoria.getUsuario() != null) {
            Usuario usuarioAlvo = mercadoria.getUsuario();
            permissionChecker.checkUserAccess(usuarioAlvo, requester);
        } else {
            Empresa empresa = mercadoria.getEmpresa();
            permissionChecker.isFromEmpresa(empresa, requester);
        }
        MercadoriaReturnDTO mercadoriaDTO = mercadoriaMapper.toReturnDTO(mercadoria);
        return mercadoriaLoader.getQuantidade(mercadoriaDTO);
    }

    @Override
    @Transactional
    public List<MercadoriaReturnDTO> selecionarTodos() {
        return mercadoriaRepositorio.findAll()
            .stream()
            .map(mercadoriaMapper::toReturnDTO)
            .map(mercadoriaLoader::getQuantidade)
            .toList();
    }

    @Transactional
    public List<MercadoriaReturnDTO> selecionarFromUsuario(Long id, RequesterDTO requester) {
        List<Mercadoria> mercadorias = mercadoriaRepositorio.findByUsuarioId(id);
        Usuario usuarioAlvo = mercadorias.get(0).getUsuario();
        permissionChecker.checkUserAccess(usuarioAlvo, requester);
        return mercadorias
            .stream()
            .map(mercadoriaMapper::toReturnDTO)
            .map(mercadoriaLoader::getQuantidade)
            .toList();
    }

    @Transactional
    public List<MercadoriaReturnDTO> selecionarFromEmpresa(Long id, RequesterDTO requester) {
        List<Mercadoria> mercadorias = mercadoriaRepositorio.findByEmpresaId(id);
        Empresa empresa = mercadorias.get(0).getEmpresa();
        permissionChecker.isFromEmpresa(empresa, requester);
        return mercadorias
            .stream()
            .map(mercadoriaMapper::toReturnDTO)
            .map(mercadoriaLoader::getQuantidade)
            .toList();
    }

    @Override
    @Transactional
    public MercadoriaReturnDTO atualizar(Long id, MercadoriaDTO novosDados, RequesterDTO requester) {
        Mercadoria mercadoriaAlvo = mercadoriaRepositorio.findById(id).get();
        if (mercadoriaAlvo.getUsuario() != null) {
            Usuario usuarioAlvo = mercadoriaAlvo.getUsuario();
            permissionChecker.checkUserAccess(usuarioAlvo, requester);
        } else {
            Empresa empresa = mercadoriaAlvo.getEmpresa();
            permissionChecker.isFromEmpresa(empresa, requester);
        }
        MercadoriaReturnDTO atualizada = mercadoriaMapper.toReturnDTO(
            mercadoriaAtualizar.atualizar(mercadoriaAlvo, novosDados)
        );
        return mercadoriaLoader.getQuantidade(atualizada);
    }

    @Transactional
    public MercadoriaReturnDTO atualizarEstoque(Long id, MercadoriaEstoqueDTO novosDados, RequesterDTO requester) {
        Mercadoria mercadoria = mercadoriaRepositorio.findById(id).get();
        if (mercadoria.getUsuario() != null) {
            Usuario usuarioAlvo = mercadoria.getUsuario();
            permissionChecker.checkUserAccess(usuarioAlvo, requester);
        } else {
            Empresa empresa = mercadoria.getEmpresa();
            permissionChecker.isFromEmpresa(empresa, requester);
        }
        mercadoriaAtualizarEstoque.atualizarEstoque(mercadoria, novosDados);
        return mercadoriaLoader.getQuantidade(
            mercadoriaMapper.toReturnDTO(mercadoria)
        );
    }

    @Override
    public void deletar(Long id, RequesterDTO requester) {
        Mercadoria mercadoria = mercadoriaRepositorio.findById(id).get();
        if (mercadoria.getUsuario() != null) {
            Usuario usuarioAlvo = mercadoria.getUsuario();
            permissionChecker.checkUserAccess(usuarioAlvo, requester);
        } else {
            Empresa empresa = mercadoria.getEmpresa();
            permissionChecker.isFromEmpresa(empresa, requester);
        }
        mercadoriaRemover.removerMercadoria(mercadoria);
    }
}
