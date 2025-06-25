package com.autobots.app.modelos.mercadoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Mercadoria;
import com.autobots.app.repositorios.mercadoria.MercadoriaRepositorio;
import com.autobots.app.types.dtos.MercadoriaDTO;
import com.autobots.app.utils.mappers.MercadoriaMapper;

import jakarta.transaction.Transactional;

@Component
public class MercadoriaAtualizar {
    @Autowired
    private MercadoriaRepositorio mercadoriaRepositorio;

    @Autowired
    private MercadoriaMapper mercadoriaMapper;

    @Transactional
    public Mercadoria atualizar(Mercadoria mercadoria, MercadoriaDTO novosDados) {
        mercadoriaMapper.updateMercadoria(novosDados, mercadoria);
        return mercadoriaRepositorio.save(mercadoria);
    }
}
