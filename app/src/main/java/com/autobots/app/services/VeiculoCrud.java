package com.autobots.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.app.entidades.Usuario;
import com.autobots.app.entidades.Veiculo;
import com.autobots.app.modelos.veiculo.VeiculoAtualizar;
import com.autobots.app.modelos.veiculo.VeiculoRemover;
import com.autobots.app.repositorios.VeiculoRepositorio;
import com.autobots.app.types.dtos.RequesterDTO;
import com.autobots.app.types.dtos.VeiculoDTO;
import com.autobots.app.types.dtos.VeiculoReturnDTO;
import com.autobots.app.types.interfaces.CrudInterface;
import com.autobots.app.utils.CrudPermissionChecker;
import com.autobots.app.utils.mappers.VeiculoMapper;

import jakarta.transaction.Transactional;

@Service
public class VeiculoCrud implements CrudInterface<VeiculoReturnDTO, Long, VeiculoDTO, RequesterDTO>{
    @Autowired
    private VeiculoRepositorio veiculoRepositorio;

    @Autowired
    private VeiculoAtualizar veiculoAtualizar;
    @Autowired
    private VeiculoRemover veiculoRemover;

    @Autowired
    private VeiculoMapper veiculoMapper;

    @Autowired
    private CrudPermissionChecker permissionChecker;

    @Override
    @Transactional
    public VeiculoReturnDTO selecionar(Long id, RequesterDTO requester) {
        Veiculo veiculo = veiculoRepositorio.findById(id).get();
        Usuario usuario = veiculo.getUsuario();

        permissionChecker.checkUserAccess(usuario, requester);

        return veiculoMapper.toReturnDTO(veiculo);
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
    public List<VeiculoReturnDTO> selecionarFromUsuario(Long id, RequesterDTO requester) {
        List<Veiculo> veiculos = veiculoRepositorio.findByUsuarioId(id);
        Usuario usuario = veiculos.get(0).getUsuario();

        permissionChecker.checkUserAccess(usuario, requester);
        
        return veiculos
            .stream()
            .map(veiculoMapper::toReturnDTO)
            .toList();
    }

    @Override
    @Transactional
    public VeiculoReturnDTO atualizar(Long id, VeiculoDTO veiculoDTO, RequesterDTO requester) {
        Veiculo veiculoAlvo = veiculoRepositorio.findById(id).get();
        Usuario usuario = veiculoAlvo.getUsuario();

        permissionChecker.checkUserAccess(usuario, requester);

        return veiculoMapper.toReturnDTO(
                veiculoAtualizar.atualizar(veiculoAlvo, veiculoDTO)
            );
    }

    @Override
    @Transactional
    public void deletar(Long id, RequesterDTO requester) {
        Veiculo veiculoAlvo = veiculoRepositorio.findById(id).get();
        Usuario usuario = veiculoAlvo.getUsuario();

        permissionChecker.checkUserAccess(usuario, requester);

        veiculoRemover.removerVeiculo(veiculoAlvo);
    }
}
