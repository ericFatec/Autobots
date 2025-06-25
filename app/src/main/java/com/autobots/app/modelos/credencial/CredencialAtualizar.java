package com.autobots.app.modelos.credencial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Credencial;
import com.autobots.app.entidades.CredencialCodigoDeBarras;
import com.autobots.app.entidades.CredencialUsuarioSenha;
import com.autobots.app.repositorios.usuario.CredencialRepositorio;
import com.autobots.app.types.dtos.CredencialDTO;
import com.autobots.app.utils.validators.StringVerificador;

@Component
public class CredencialAtualizar {
    @Autowired
    private CredencialRepositorio credencialRepositorio;

    public Credencial atualizar(Credencial credencial, CredencialDTO novosDados) {
        if (credencial instanceof CredencialUsuarioSenha credencialSenha) {
            if (StringVerificador.isValid(novosDados.getLogin())) {
                credencialSenha.setLogin(novosDados.getLogin());
            }
            if (StringVerificador.isValid(novosDados.getSenha())) {
                credencialSenha.setSenha(novosDados.getSenha());
            }
            return credencialRepositorio.save(credencialSenha);
        } else if (credencial instanceof CredencialCodigoDeBarras credencialCodigo) {
            if (StringVerificador.isValid(novosDados.getCodigo())) {
                credencialCodigo.setCodigo(novosDados.getCodigo());
            }
            return credencialRepositorio.save(credencialCodigo);
        }
        return null;
    }
}
