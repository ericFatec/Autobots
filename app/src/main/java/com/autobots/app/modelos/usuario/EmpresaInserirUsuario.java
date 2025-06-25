package com.autobots.app.modelos.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.entidades.Usuario;
import com.autobots.app.repositorios.usuario.UsuarioRepositorio;
import com.autobots.app.types.dtos.UsuarioCadastroDTO;

@Component
public class EmpresaInserirUsuario {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private UsuarioInsercao usuarioInsercao;

    public Usuario inserirUsuario(Empresa empresa, UsuarioCadastroDTO usuarioCadastroDTO) {
        Usuario usuario = usuarioInsercao.inserir(usuarioCadastroDTO);
        usuario.setEmpresa(empresa);
        return usuarioRepositorio.save(usuario);
    }
}
