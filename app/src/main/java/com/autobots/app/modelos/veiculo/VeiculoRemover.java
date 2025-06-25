package com.autobots.app.modelos.veiculo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Usuario;
import com.autobots.app.entidades.Veiculo;
import com.autobots.app.repositorios.usuario.UsuarioRepositorio;

import jakarta.transaction.Transactional;

@Component
public class VeiculoRemover {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void removerVeiculo(Veiculo veiculo) {
        Usuario usuario = veiculo.getUsuario();
        usuario.getVeiculos().removeIf(v -> v.getId().equals(veiculo.getId()));
        usuarioRepositorio.save(usuario);
    }
}
