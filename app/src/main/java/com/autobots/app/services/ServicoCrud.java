package com.autobots.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.app.entidades.Servico;
import com.autobots.app.modelos.servico.ServicoAtualizar;
import com.autobots.app.modelos.servico.ServicoRemover;
import com.autobots.app.repositorios.ServicoRepositorio;
import com.autobots.app.types.dtos.ServicoDTO;
import com.autobots.app.types.dtos.ServicoReturnDTO;
import com.autobots.app.types.interfaces.CrudInterface;
import com.autobots.app.utils.mappers.ServicoMapper;

import jakarta.transaction.Transactional;

@Service
public class ServicoCrud implements CrudInterface<ServicoReturnDTO, Long, ServicoDTO>{
    @Autowired
    private ServicoRepositorio servicoRepositorio;

    @Autowired
    private ServicoAtualizar servicoAtualizar;
    @Autowired
    private ServicoRemover servicoRemover;

    @Autowired
    private ServicoMapper servicoMapper;

    @Override
    @Transactional
    public ServicoReturnDTO selecionar(Long id) {
        ServicoReturnDTO servicoReturn = servicoMapper.toReturnDTO(
            servicoRepositorio.findById(id).get()
        );
        return servicoReturn;
    }

    @Override
    @Transactional
    public List<ServicoReturnDTO> selecionarTodos() {
        return servicoRepositorio.findAll()
            .stream()
            .map(servicoMapper::toReturnDTO)
            .toList();
    }

    @Transactional
    public List<ServicoReturnDTO> selecionarFromEmpresa(Long id) {
        return servicoRepositorio.findByEmpresaId(id)
            .stream()
            .map(servicoMapper::toReturnDTO)
            .toList();
    }

    @Override
    @Transactional
    public ServicoReturnDTO atualizar(Long id, ServicoDTO novosDados) {
        Servico servicoAlvo = servicoRepositorio.findById(id).get();
        ServicoReturnDTO servicoReturn = servicoMapper.toReturnDTO(
            servicoAtualizar.atualizar(servicoAlvo, novosDados)
        );
        return servicoReturn;
    }

    @Override
    public void deletar(Long id) {
        servicoRemover.removerServico(id);
    }
}
