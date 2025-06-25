package com.autobots.app.modelos.telefone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Telefone;
import com.autobots.app.repositorios.telefone.TelefoneRepositorio;
import com.autobots.app.types.dtos.TelefoneDTO;
import com.autobots.app.utils.StringVerificador;

import jakarta.annotation.PostConstruct;

@Component
public class TelefoneAtualizar {

    @Autowired
    private TelefoneRepositorio telefoneRepositorio;

    private StringVerificador stringVerificador;

    @PostConstruct
    public void init() {
        this.stringVerificador = new StringVerificador();
    }

    public Telefone atualizar(Telefone telefone, TelefoneDTO novosDados) {
        if (
            novosDados.getDdd() != null &&
            !novosDados.getDdd().isEmpty() &&
            stringVerificador.verificar(novosDados.getDdd())
        ){
            telefone.setDdd(novosDados.getDdd());
        }
        if (
            novosDados.getNumero() != null &&
            !novosDados.getNumero().isEmpty() &&
            stringVerificador.verificar(novosDados.getNumero())
        ){
            telefone.setNumero(novosDados.getNumero());
        }

        return telefoneRepositorio.save(telefone);
    }
}
