package com.autobots.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.app.entidades.Veiculo;
import com.autobots.app.modelos.veiculo.VeiculoAtualizar;
import com.autobots.app.modelos.veiculo.VeiculoRemover;
import com.autobots.app.repositorios.VeiculoRepositorio;
import com.autobots.app.types.dtos.VeiculoDTO;
import com.autobots.app.types.dtos.VeiculoReturnDTO;
import com.autobots.app.types.interfaces.CrudInterface;
import com.autobots.app.utils.mappers.VeiculoMapper;

import jakarta.transaction.Transactional;

@Service
public class VeiculoCrud implements CrudInterface<VeiculoReturnDTO, Long, VeiculoDTO>{
    @Autowired
    private VeiculoRepositorio veiculoRepositorio;

    @Autowired
    private VeiculoAtualizar veiculoAtualizar;
    @Autowired
    private VeiculoRemover veiculoRemover;

    @Autowired
    private VeiculoMapper veiculoMapper;

    @Override
    @Transactional
    public VeiculoReturnDTO selecionar(Long id) {
        return veiculoMapper.toReturnDTO(
                veiculoRepositorio.findById(id).get()
            );
    }

    @Override
    @Transactional
    public List<VeiculoReturnDTO> selecionarTodos() {
        return veiculoRepositorio.findAll()
            .stream()
            .map(veiculoMapper::toReturnDTO)
            .toList();
    }

    @Transactional
    public List<VeiculoReturnDTO> selecionarFromUsuario(Long id) {
        return veiculoRepositorio.findByUsuarioId(id)
            .stream()
            .map(veiculoMapper::toReturnDTO)
            .toList();
    }

    @Override
    @Transactional
    public VeiculoReturnDTO atualizar(Long id, VeiculoDTO veiculoDTO) {
        Veiculo veiculoAlvo = veiculoRepositorio.findById(id).get();
        return veiculoMapper.toReturnDTO(
                veiculoAtualizar.atualizar(veiculoAlvo, veiculoDTO)
            );
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        veiculoRemover.removerVeiculo(id);
    }
}
