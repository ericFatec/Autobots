package com.autobots.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.app.entidades.Mercadoria;
import com.autobots.app.modelos.mercadoria.MercadoriaAtualizar;
import com.autobots.app.modelos.mercadoria.MercadoriaAtualizarEstoque;
import com.autobots.app.modelos.mercadoria.MercadoriaLoader;
import com.autobots.app.modelos.mercadoria.MercadoriaRemover;
import com.autobots.app.repositorios.mercadoria.MercadoriaRepositorio;
import com.autobots.app.types.dtos.MercadoriaDTO;
import com.autobots.app.types.dtos.MercadoriaEstoqueDTO;
import com.autobots.app.types.dtos.MercadoriaReturnDTO;
import com.autobots.app.types.interfaces.CrudInterface;
import com.autobots.app.utils.mappers.MercadoriaMapper;

import jakarta.transaction.Transactional;

@Service
public class MercadoriaCrud implements CrudInterface<MercadoriaReturnDTO, Long, MercadoriaDTO>{
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
    
    @Override
    @Transactional
    public MercadoriaReturnDTO selecionar(Long id) {
        Mercadoria mercadoria = mercadoriaRepositorio.findById(id).get();
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
    public List<MercadoriaReturnDTO> selecionarFromUsuario(Long id) {
        return mercadoriaRepositorio.findByUsuarioId(id)
            .stream()
            .map(mercadoriaMapper::toReturnDTO)
            .map(mercadoriaLoader::getQuantidade)
            .toList();
    }

    @Transactional
    public List<MercadoriaReturnDTO> selecionarFromEmpresa(Long id) {
        return mercadoriaRepositorio.findByEmpresaId(id)
            .stream()
            .map(mercadoriaMapper::toReturnDTO)
            .map(mercadoriaLoader::getQuantidade)
            .toList();
    }

    @Override
    @Transactional
    public MercadoriaReturnDTO atualizar(Long id, MercadoriaDTO novosDados) {
        Mercadoria mercadoriaAlvo = mercadoriaRepositorio.findById(id).get();
        MercadoriaReturnDTO atualizada = mercadoriaMapper.toReturnDTO(
            mercadoriaAtualizar.atualizar(mercadoriaAlvo, novosDados)
        );
        return mercadoriaLoader.getQuantidade(atualizada);
    }

    @Transactional
    public MercadoriaReturnDTO atualizarEstoque(Long id, MercadoriaEstoqueDTO novosDados) {
        Mercadoria mercadoria = mercadoriaRepositorio.findById(id).get();
        mercadoriaAtualizarEstoque.atualizarEstoque(mercadoria, novosDados);
        return mercadoriaLoader.getQuantidade(
            mercadoriaMapper.toReturnDTO(mercadoria)
        );
    }

    @Override
    public void deletar(Long id) {
        mercadoriaRemover.removerMercadoria(id);
    }
}
