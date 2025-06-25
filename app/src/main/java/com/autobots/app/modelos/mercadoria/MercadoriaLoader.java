package com.autobots.app.modelos.mercadoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.EmpresaEstoque;
import com.autobots.app.entidades.UsuarioEstoque;
import com.autobots.app.repositorios.mercadoria.EmpresaEstoqueRepositorio;
import com.autobots.app.repositorios.mercadoria.UsuarioEstoqueRepositorio;
import com.autobots.app.types.dtos.MercadoriaReturnDTO;

@Component
public class MercadoriaLoader {
    @Autowired
    UsuarioEstoqueRepositorio usuarioEstoqueRepositorio;
    @Autowired
    EmpresaEstoqueRepositorio empresaEstoqueRepositorio;

    public MercadoriaReturnDTO getQuantidade(MercadoriaReturnDTO mercadoria) {
        Long mercadoriaId = mercadoria.getId();
        Long usuarioId = mercadoria.getUsuarioId();
        Long empresaId = mercadoria.getEmpresaId();
        if (usuarioId != null) {
            mercadoria.setQuantidade(getQuantidadeUsuario(usuarioId, mercadoriaId));
        } else {
            mercadoria.setQuantidade(getQuantidadeEmpresa(empresaId, mercadoriaId));
        }
        return mercadoria;
    }

    private Integer getQuantidadeUsuario(Long usuarioId, Long mercadoriaId) {
        return usuarioEstoqueRepositorio
            .findByUsuarioIdAndMercadoriaId(usuarioId, mercadoriaId)
            .map(UsuarioEstoque::getQuantidade)
            .orElse(0);
    }

    private Integer getQuantidadeEmpresa(Long empresaId, Long mercadoriaId) {
        return empresaEstoqueRepositorio
            .findByEmpresaIdAndMercadoriaId(empresaId, mercadoriaId)
            .map(EmpresaEstoque::getQuantidade)
            .orElse(0);
    }
}
