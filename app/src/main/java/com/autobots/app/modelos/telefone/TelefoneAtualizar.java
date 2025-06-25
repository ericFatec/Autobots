package com.autobots.app.modelos.telefone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Telefone;
import com.autobots.app.repositorios.TelefoneRepositorio;
import com.autobots.app.types.dtos.TelefoneDTO;
import com.autobots.app.utils.mappers.TelefoneMapper;

import jakarta.transaction.Transactional;

@Component
public class TelefoneAtualizar {

    @Autowired
    private TelefoneRepositorio telefoneRepositorio;

    @Autowired
    private TelefoneMapper telefoneMapper;

    @Transactional
    public Telefone atualizar(Telefone telefone, TelefoneDTO novosDados) {
        telefoneMapper.updateTelefone(novosDados, telefone);
        return telefoneRepositorio.save(telefone);
    }
}
