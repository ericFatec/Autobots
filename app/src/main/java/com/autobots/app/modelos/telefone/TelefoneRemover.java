package com.autobots.app.modelos.telefone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.entidades.Telefone;
import com.autobots.app.entidades.Usuario;
import com.autobots.app.repositorios.EmpresaRepositorio;
import com.autobots.app.repositorios.usuario.UsuarioRepositorio;

import jakarta.transaction.Transactional;

@Component
public class TelefoneRemover {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @Transactional
    public void removerTelefone(Telefone telefone) {
        if (telefone.getUsuario() != null) {
            Usuario usuario = telefone.getUsuario();
            usuario.getTelefones().removeIf(tel -> tel.getId().equals(telefone.getId()));
            usuarioRepositorio.save(usuario);
        } else {
            Empresa empresa = telefone.getEmpresa();
            empresa.getTelefones().removeIf(tel -> tel.getId().equals(telefone.getId()));
            empresaRepositorio.save(empresa);
        }
    }
}
