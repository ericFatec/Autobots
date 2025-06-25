package com.autobots.app.modelos.mercadoria;
import com.autobots.app.repositorios.mercadoria.UsuarioEstoqueRepositorio;
import com.autobots.app.repositorios.usuario.UsuarioRepositorio;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.entidades.Mercadoria;
import com.autobots.app.entidades.Usuario;
import com.autobots.app.repositorios.EmpresaRepositorio;
import com.autobots.app.repositorios.mercadoria.EmpresaEstoqueRepositorio;

@Component
public class MercadoriaRemover {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private UsuarioEstoqueRepositorio usuarioEstoqueRepositorio;
    @Autowired
    private EmpresaRepositorio empresaRepositorio;
    @Autowired
    private EmpresaEstoqueRepositorio empresaEstoqueRepositorio;

    MercadoriaRemover(UsuarioEstoqueRepositorio usuarioEstoqueRepositorio) {
        this.usuarioEstoqueRepositorio = usuarioEstoqueRepositorio;
    }

    @Transactional
    public void removerMercadoria(Mercadoria mercadoria) {
        Long id = mercadoria.getId();
        if (mercadoria.getUsuario() != null) {
            usuarioEstoqueRepositorio.deleteAllByMercadoriaId(id);
            Usuario usuario = mercadoria.getUsuario();
            usuario.getMercadorias().removeIf(m -> m.getId().equals(id));
            usuarioRepositorio.save(usuario);
        } else {
            empresaEstoqueRepositorio.deleteAllByMercadoriaId(id);
            Empresa empresa = mercadoria.getEmpresa();
            empresa.getMercadorias().removeIf(m -> m.getId().equals(id));
            empresaRepositorio.save(empresa);
        }
    }
}
