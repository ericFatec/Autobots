package com.autobots.app.modelos.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Usuario;
import com.autobots.app.repositorios.usuario.UsuarioRepositorio;
import com.autobots.app.types.dtos.UsuarioAtualizarDTO;
import com.autobots.app.utils.mappers.UsuarioMapper;

import jakarta.transaction.Transactional;

@Component
public class UsuarioAtualizar {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Transactional
    public Usuario atualizar(Usuario usuario, UsuarioAtualizarDTO novosDados) {
        usuarioMapper.updateUsuario(novosDados, usuario);
        
        return usuarioRepositorio.save(usuario);
    }
}
