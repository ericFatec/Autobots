package com.autobots.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.entidades.Servico;
import com.autobots.app.modelos.servico.ServicoAtualizar;
import com.autobots.app.modelos.servico.ServicoRemover;
import com.autobots.app.repositorios.ServicoRepositorio;
import com.autobots.app.types.dtos.RequesterDTO;
import com.autobots.app.types.dtos.ServicoDTO;
import com.autobots.app.types.dtos.ServicoReturnDTO;
import com.autobots.app.types.interfaces.CrudInterface;
import com.autobots.app.utils.CrudPermissionChecker;
import com.autobots.app.utils.mappers.ServicoMapper;

import jakarta.transaction.Transactional;

@Service
public class ServicoCrud implements CrudInterface<ServicoReturnDTO, Long, ServicoDTO, RequesterDTO>{
    @Autowired
    private ServicoRepositorio servicoRepositorio;

    @Autowired
    private ServicoAtualizar servicoAtualizar;
    @Autowired
    private ServicoRemover servicoRemover;

    @Autowired
    private ServicoMapper servicoMapper;

    @Autowired
    private CrudPermissionChecker permissionChecker;

    @Override
    @Transactional
    public ServicoReturnDTO selecionar(Long id, RequesterDTO requester) {
        Servico servico = servicoRepositorio.findById(id).get();
        Empresa empresa = servico.getEmpresa();

        permissionChecker.isFromEmpresa(empresa, requester);

        ServicoReturnDTO servicoReturn = servicoMapper.toReturnDTO(servico);
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
    public List<ServicoReturnDTO> selecionarFromEmpresa(Long id, RequesterDTO requester) {
        List<Servico> servicos = servicoRepositorio.findByEmpresaId(id);
        Empresa empresa = servicos.get(0).getEmpresa();

        permissionChecker.isFromEmpresa(empresa, requester);

        return servicos
            .stream()
            .map(servicoMapper::toReturnDTO)
            .toList();
    }

    @Override
    @Transactional
    public ServicoReturnDTO atualizar(Long id, ServicoDTO novosDados, RequesterDTO requester) {
        Servico servicoAlvo = servicoRepositorio.findById(id).get();
        Empresa empresa = servicoAlvo.getEmpresa();

        permissionChecker.isFromEmpresa(empresa, requester);

        ServicoReturnDTO servicoReturn = servicoMapper.toReturnDTO(
            servicoAtualizar.atualizar(servicoAlvo, novosDados)
        );
        return servicoReturn;
    }

    @Override
    public void deletar(Long id, RequesterDTO requester) {
        Servico servicoAlvo = servicoRepositorio.findById(id).get();
        Empresa empresa = servicoAlvo.getEmpresa();

        permissionChecker.isFromEmpresa(empresa, requester);

        servicoRemover.removerServico(servicoAlvo);
    }
}
