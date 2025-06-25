package com.autobots.app.modelos.credencial;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.adapters.UserDetailsImpl;
import com.autobots.app.entidades.Credencial;
import com.autobots.app.entidades.CredencialCodigoDeBarras;
import com.autobots.app.entidades.CredencialUsuarioSenha;
import com.autobots.app.exceptions.InactiveCredentialException;
import com.autobots.app.exceptions.InvalidCredentialsException;
import com.autobots.app.repositorios.usuario.CredencialRepositorio;
import com.autobots.app.types.dtos.CredencialDTO;
import com.autobots.app.utils.HashUtil;

import jakarta.transaction.Transactional;

@Component
public class UsuarioCredencialLogin {
    @Autowired
    private CredencialRepositorio credencialRepositorio;

    @Transactional
    public UserDetailsImpl login(CredencialDTO credencial) {
        return switch (credencial.getTipo()) {
            case SENHA -> loginComSenha(credencial);
            case CODIGO -> process(
                credencialRepositorio
                    .findByCodigo(credencial.getCodigo())
            );
            default -> throw new RuntimeException("Tipo de credencial inválido");
        };
    }

    @Transactional
    public UserDetailsImpl loadUser(String username) {
        Optional<CredencialUsuarioSenha> byLogin = credencialRepositorio.findByLogin(username);
        if (byLogin.isPresent()) {
            CredencialUsuarioSenha credencial = byLogin.get();
            return new UserDetailsImpl(credencial.getUsuario(), credencial);
        }
        Optional<CredencialCodigoDeBarras> byCodigo = credencialRepositorio.findByCodigo(username);
        CredencialCodigoDeBarras credencial = byCodigo.get();
        return new UserDetailsImpl(credencial.getUsuario(), credencial);
    }

    @Transactional
    private UserDetailsImpl loginComSenha(CredencialDTO dto) {
        Optional<CredencialUsuarioSenha> opt = credencialRepositorio.findByLogin(dto.getLogin().trim());

        CredencialUsuarioSenha credencial = opt
            .filter(c -> HashUtil.matches(dto.getSenha(), c.getSenha()))
            .orElseThrow(InvalidCredentialsException::new);

        return process(Optional.of(credencial));
    }

    @Transactional
    private UserDetailsImpl process(Optional<? extends Credencial> opt) {

        Credencial credencial = opt.orElseThrow(InvalidCredentialsException::new);

        if (credencial.isInativo()) {
            throw new InactiveCredentialException();
        }

        credencial.setUltimoAcesso(Instant.now());
        return new UserDetailsImpl(credencial.getUsuario(), credencialRepositorio.save(credencial));
    }
}
