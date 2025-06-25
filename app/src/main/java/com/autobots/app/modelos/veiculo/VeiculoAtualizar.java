package com.autobots.app.modelos.veiculo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Veiculo;
import com.autobots.app.repositorios.VeiculoRepositorio;
import com.autobots.app.types.dtos.VeiculoDTO;
import com.autobots.app.utils.mappers.VeiculoMapper;

import jakarta.transaction.Transactional;

@Component
public class VeiculoAtualizar {
    @Autowired
    private VeiculoRepositorio veiculoRepositorio;

    @Autowired
    private VeiculoMapper veiculoMapper;

    @Transactional
    public Veiculo atualizar(Veiculo veiculo, VeiculoDTO veiculoDTO) {
        veiculoMapper.updateVeiculo(veiculoDTO, veiculo);
        return veiculoRepositorio.save(veiculo);
    }
}
