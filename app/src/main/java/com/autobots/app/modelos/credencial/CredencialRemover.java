package com.autobots.app.modelos.credencial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Credencial;
import com.autobots.app.entidades.Usuario;
import com.autobots.app.repositorios.usuario.UsuarioRepositorio;

import jakarta.transaction.Transactional;

@Component
public class CredencialRemover {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void removerCredencial(Credencial credencial) {
        Usuario usuario = credencial.getUsuario();
        usuario.getCredenciais().removeIf(c -> c.getId().equals(credencial.getId()));
        usuarioRepositorio.save(usuario);
    }
}
