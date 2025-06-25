package com.autobots.app.modelos.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Servico;
import com.autobots.app.repositorios.ServicoRepositorio;
import com.autobots.app.types.dtos.ServicoDTO;
import com.autobots.app.utils.mappers.ServicoMapper;

import jakarta.transaction.Transactional;

@Component
public class ServicoAtualizar {
    @Autowired
    private ServicoRepositorio servicoRepositorio;

    @Autowired
    private ServicoMapper servicoMapper;

    @Transactional
    public Servico atualizar(Servico servico, ServicoDTO novosDados) {
        servicoMapper.updateServico(novosDados, servico);
        return servicoRepositorio.save(servico);
    }
}
