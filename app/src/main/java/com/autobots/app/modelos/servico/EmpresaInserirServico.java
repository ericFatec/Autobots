package com.autobots.app.modelos.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.entidades.Servico;
import com.autobots.app.repositorios.ServicoRepositorio;
import com.autobots.app.types.dtos.ServicoDTO;
import com.autobots.app.utils.mappers.ServicoMapper;

import jakarta.transaction.Transactional;

@Component
public class EmpresaInserirServico {
    @Autowired
    private ServicoRepositorio servicoRepositorio;

    @Autowired
    private ServicoMapper servicoMapper;

    @Transactional
    public Servico inserirServico(Empresa empresa, ServicoDTO servicoDTO) {
        Servico servico = servicoMapper.toObject(servicoDTO);
        servico.setEmpresa(empresa);
        return servicoRepositorio.save(servico);
    }
}
