package com.autobots.app.repositorios.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.app.entidades.Credencial;
import com.autobots.app.entidades.CredencialCodigoDeBarras;
import com.autobots.app.entidades.CredencialUsuarioSenha;

public interface CredencialRepositorio extends JpaRepository<Credencial, Long>{
    List<Credencial> findByUsuarioId(Long usuarioId);

    Optional<CredencialUsuarioSenha> findByLoginAndSenha(String login, String senha);
    Optional<CredencialCodigoDeBarras> findByCodigo(String codigo);
}
