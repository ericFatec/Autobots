package com.autobots.app.modelos.venda;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Usuario;
import com.autobots.app.entidades.Vendas;
import com.autobots.app.entidades.snapshots.UsuarioSnapshot;
import com.autobots.app.repositorios.VendaRepositorio;
import com.autobots.app.utils.mappers.UsuarioMapper;

import jakarta.transaction.Transactional;

@Component
public class VendaRemoverUsuario {

    @Autowired private VendaRepositorio vendaRepositorio;

    @Autowired private UsuarioMapper usuarioMapper;

    @Transactional
    public void removeUsuario(Usuario usuario) {

        Long userId = usuario.getId();

        List<Vendas> vendas = vendaRepositorio
                .findByClienteIdOrVendedorId(userId, userId);

        if (vendas.isEmpty()) return;

        UsuarioSnapshot snapshot = usuarioMapper.toSnapshot(usuario);

        for (Vendas venda : vendas) {

            if (venda.getCliente() != null &&
                userId.equals(venda.getCliente().getId())) {

                venda.setCliente(null);
                venda.setUsuarioDeletado(snapshot);
            }

            if (venda.getVendedor() != null &&
                userId.equals(venda.getVendedor().getId())) {

                venda.setVendedor(null);
                venda.setUsuarioDeletado(snapshot);
            }

            if (venda.getCliente() == null && venda.getVendedor() == null) {
                vendaRepositorio.delete(venda);
            } else {
                vendaRepositorio.save(venda);
            }
        }
    }
}
