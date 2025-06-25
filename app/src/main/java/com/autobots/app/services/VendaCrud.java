package com.autobots.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.app.entidades.Usuario;
import com.autobots.app.modelos.venda.VendaInserir;
import com.autobots.app.repositorios.VendaRepositorio;
import com.autobots.app.repositorios.usuario.UsuarioRepositorio;
import com.autobots.app.types.dtos.VendaDTO;
import com.autobots.app.types.dtos.VendaReturnDTO;
import com.autobots.app.types.interfaces.CrudInterface;
import com.autobots.app.utils.mappers.VendaMapper;

import jakarta.transaction.Transactional;

@Service
public class VendaCrud implements CrudInterface<VendaReturnDTO, Long, VendaDTO>{

    @Autowired
    private VendaRepositorio vendaRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private VendaInserir vendaInserir;

    @Autowired
    private VendaMapper vendaMapper;

    @Transactional
    public VendaReturnDTO inserir(VendaDTO venda) {
        return vendaMapper.toReturnDTO(
            vendaInserir.inserir(venda)
        );
    }

    @Override
    @Transactional
    public VendaReturnDTO selecionar(Long id) {
        return vendaMapper.toReturnDTO(
            vendaRepositorio.findById(id).get()  
        );
    }

    @Override
    @Transactional
    public List<VendaReturnDTO> selecionarTodos() {
        return vendaRepositorio.findAll()
            .stream()
            .map(vendaMapper::toReturnDTO)
            .toList();
    }

    @Transactional
    public List<VendaReturnDTO> selecionarFromUsuario(Long id) {
        return vendaRepositorio.findByClienteIdOrVendedorId(id, id)
            .stream()
            .map(vendaMapper::toReturnDTO)
            .toList();
    }

    @Transactional
    public List<VendaReturnDTO> selecionarFromEmpresa(Long id) {
        List<Usuario> usuarios = usuarioRepositorio.findByEmpresaId(id);
        return vendaRepositorio.findByClienteInOrVendedorIn(usuarios, usuarios)
            .stream()
            .map(vendaMapper::toReturnDTO)
            .toList();
    }

    @Override
    public VendaReturnDTO atualizar(Long id, VendaDTO novosDados) {
        return new VendaReturnDTO();
    }

    @Override
    public void deletar(Long id) {
        vendaRepositorio.deleteById(id);
    }
}
