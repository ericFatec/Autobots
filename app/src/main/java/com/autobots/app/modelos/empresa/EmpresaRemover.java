package com.autobots.app.modelos.empresa;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.entidades.Usuario;
import com.autobots.app.modelos.venda.VendaRemoverUsuario;
import com.autobots.app.repositorios.EmpresaRepositorio;

@Component
public class EmpresaRemover {
    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @Autowired
    private VendaRemoverUsuario vendaRemoverUsuario;

    public void removerEmpresa(Empresa empresa) {
        Set<Usuario> usuarios = empresa.getUsuarios();

        for (Usuario usuario : usuarios) {
            vendaRemoverUsuario.removeUsuario(usuario);
        }

        empresaRepositorio.delete(empresa);
    }
}
