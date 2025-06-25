package com.autobots.app.modelos.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.entidades.Usuario;
import com.autobots.app.modelos.venda.VendaRemoverUsuario;
import com.autobots.app.repositorios.EmpresaRepositorio;
import com.autobots.app.repositorios.usuario.UsuarioRepositorio;

@Component
public class UsuarioRemover {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @Autowired
    private VendaRemoverUsuario vendaRemoverUsuario;

    public void removerUsuario(Usuario usuario) {
        vendaRemoverUsuario.removeUsuario(usuario);
        if (usuario.getEmpresa() != null) {
            Empresa empresa = usuario.getEmpresa();
            empresa.getUsuarios().removeIf(user -> user.getId().equals(usuario.getId()));
            empresaRepositorio.save(empresa);
        } else {
            usuarioRepositorio.delete(usuario);
        }
    }
}
