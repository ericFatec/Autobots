package com.autobots.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.entidades.Usuario;
import com.autobots.app.entidades.Vendas;
import com.autobots.app.modelos.venda.VendaInserir;
import com.autobots.app.repositorios.VendaRepositorio;
import com.autobots.app.repositorios.usuario.UsuarioRepositorio;
import com.autobots.app.types.dtos.RequesterDTO;
import com.autobots.app.types.dtos.VendaDTO;
import com.autobots.app.types.dtos.VendaReturnDTO;
import com.autobots.app.types.interfaces.CrudInterface;
import com.autobots.app.utils.CrudPermissionChecker;
import com.autobots.app.utils.mappers.VendaMapper;

import jakarta.transaction.Transactional;

@Service
public class VendaCrud implements CrudInterface<VendaReturnDTO, Long, VendaDTO, RequesterDTO>{

    @Autowired
    private VendaRepositorio vendaRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private VendaInserir vendaInserir;

    @Autowired
    private VendaMapper vendaMapper;

    @Autowired
    private CrudPermissionChecker permissionChecker;

    @Transactional
    public VendaReturnDTO inserir(VendaDTO venda, RequesterDTO requester) {
        Usuario usuarioVendedor = usuarioRepositorio.findById(venda.getVendedorId()).get();
        Usuario usuarioCliente = usuarioRepositorio.findById(venda.getClienteId()).get();
        permissionChecker.canRegisterVenda(usuarioVendedor, usuarioCliente, requester);
        return vendaMapper.toReturnDTO(
            vendaInserir.inserir(venda)
        );
    }

    @Override
    @Transactional
    public VendaReturnDTO selecionar(Long id, RequesterDTO requester) {
        Vendas venda = vendaRepositorio.findById(id).get();

        permissionChecker.canAccessVenda(venda, requester);

        return vendaMapper.toReturnDTO(venda);
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
    public List<VendaReturnDTO> selecionarFromUsuario(Long id, RequesterDTO requester) {
        List<Vendas> vendas = vendaRepositorio.findByClienteIdOrVendedorId(id, id);

        permissionChecker.canAccessVenda(vendas.get(0), requester);

        return vendas
            .stream()
            .map(vendaMapper::toReturnDTO)
            .toList();
    }

    @Transactional
    public List<VendaReturnDTO> selecionarFromEmpresa(Long id, RequesterDTO requester) {
        List<Usuario> usuarios = usuarioRepositorio.findByEmpresaId(id);
        Empresa empresa = usuarios.get(0).getEmpresa();

        permissionChecker.isFromEmpresa(empresa, requester);

        return vendaRepositorio.findByClienteInOrVendedorIn(usuarios, usuarios)
            .stream()
            .map(vendaMapper::toReturnDTO)
            .toList();
    }

    @Override
    public VendaReturnDTO atualizar(Long id, VendaDTO novosDados, RequesterDTO requester) {
        return new VendaReturnDTO();
    }

    @Override
    public void deletar(Long id, RequesterDTO requester) {
        Vendas venda = vendaRepositorio.findById(id).get();

        permissionChecker.canAccessVenda(venda, requester);

        vendaRepositorio.delete(venda);;
    }
}
