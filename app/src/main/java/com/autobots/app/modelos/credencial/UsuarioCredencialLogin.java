package com.autobots.app.modelos.credencial;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Credencial;
import com.autobots.app.entidades.Usuario;
import com.autobots.app.exceptions.InactiveCredentialException;
import com.autobots.app.exceptions.InvalidCredentialsException;
import com.autobots.app.repositorios.usuario.CredencialRepositorio;
import com.autobots.app.types.dtos.CredencialDTO;

import jakarta.transaction.Transactional;

@Component
public class UsuarioCredencialLogin {
    @Autowired
    private CredencialRepositorio credencialRepositorio;

    public Usuario login(CredencialDTO credencial) {
        return switch (credencial.getTipo()) {
            case SENHA -> process(
                credencialRepositorio
                    .findByLoginAndSenha(credencial.getLogin(), credencial.getSenha())
            );
            case CODIGO -> process(
                credencialRepositorio
                    .findByCodigo(credencial.getCodigo())
            );
            default -> throw new RuntimeException("Tipo de credencial inválido");
        };
    }

    @Transactional
    private Usuario process(Optional<? extends Credencial> opt) {

        Credencial credencial = opt.orElseThrow(InvalidCredentialsException::new);

        if (credencial.isInativo()) {
            throw new InactiveCredentialException();
        }

        return credencial.getUsuario();
    }
}
