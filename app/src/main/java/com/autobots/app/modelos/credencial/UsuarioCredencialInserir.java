package com.autobots.app.modelos.credencial;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Credencial;
import com.autobots.app.entidades.CredencialCodigoDeBarras;
import com.autobots.app.entidades.CredencialUsuarioSenha;
import com.autobots.app.entidades.Usuario;
import com.autobots.app.exceptions.InvalidCredentialsException;
import com.autobots.app.repositorios.usuario.CredencialRepositorio;
import com.autobots.app.types.dtos.CredencialDTO;
import com.autobots.app.types.enums.TipoCredencial;
import com.autobots.app.utils.HashUtil;
import com.autobots.app.utils.validators.StringVerificador;

@Component
public class UsuarioCredencialInserir {
    @Autowired
    private CredencialRepositorio credencialRepositorio;

    public Credencial inserirCredencial(Usuario usuario, CredencialDTO novaCredencialDTO) {
        TipoCredencial tipo = novaCredencialDTO.getTipo();
        switch (tipo) {
            case SENHA:
                CredencialUsuarioSenha novaCredencialSenha = new CredencialUsuarioSenha();
                novaCredencialSenha.setUsuario(usuario);
                novaCredencialSenha.setCriacao(Instant.now());
                novaCredencialSenha.setInativo(false);
                if (
                    !StringVerificador.isValid(novaCredencialDTO.getLogin()) ||
                    !StringVerificador.isValid(novaCredencialDTO.getSenha())
                ) {
                    throw new InvalidCredentialsException("Senha e login não podem ser vazios ou nulo.");
                }
                novaCredencialSenha.setLogin(novaCredencialDTO.getLogin());
                novaCredencialSenha.setSenha(
                    HashUtil.hash(novaCredencialDTO.getSenha())
                );

                return credencialRepositorio.save(novaCredencialSenha);
        
            case CODIGO:
                CredencialCodigoDeBarras novaCredencialCodigo = new CredencialCodigoDeBarras();
                novaCredencialCodigo.setUsuario(usuario);
                novaCredencialCodigo.setCriacao(Instant.now());
                novaCredencialCodigo.setInativo(false);
                if (
                    !StringVerificador.isValid(novaCredencialDTO.getCodigo())
                ) {
                    throw new InvalidCredentialsException("Código não pode ser vazio ou nulo.");
                }
                novaCredencialCodigo.setCodigo(novaCredencialDTO.getCodigo());

                return credencialRepositorio.save(novaCredencialCodigo);
            default:
                throw new IllegalArgumentException("TipoCredencial inválido: " + tipo);
        }
    }
}
