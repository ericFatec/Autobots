package com.autobots.app.utils.mappers;

import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Vendas;
import com.autobots.app.entidades.snapshots.UsuarioSnapshot;

@Component
public class VendaMapperHelper {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Named("resolveCliente")
    public UsuarioSnapshot resolveCliente(Vendas venda) {
        if (venda.getCliente() != null) {
            return usuarioMapper.toSnapshot(venda.getCliente());
        } else {
            return venda.getUsuarioDeletado();
        }
    }

    @Named("resolveVendedor")
    public UsuarioSnapshot resolveVendedor(Vendas venda) {
        if (venda.getVendedor() != null) {
            return usuarioMapper.toSnapshot(venda.getVendedor());
        } else {
            return venda.getUsuarioDeletado();
        }
    }
}
