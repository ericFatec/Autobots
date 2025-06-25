package com.autobots.app.modelos.endereco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Endereco;
import com.autobots.app.repositorios.endereco.EnderecoRepositorio;
import com.autobots.app.types.dtos.EnderecoDTO;
import com.autobots.app.utils.StringVerificador;

import jakarta.annotation.PostConstruct;

@Component
public class EnderecoAtualizar {

    @Autowired
    private EnderecoRepositorio enderecoRepositorio;

    private StringVerificador stringVerificador;

    @PostConstruct
    public void init() {
        this.stringVerificador = new StringVerificador();
    }

    public Endereco atualizar(Endereco endereco, EnderecoDTO novosDados) {
        if (
            novosDados.getBairro() != null &&
            !novosDados.getBairro().isEmpty() &&
            !stringVerificador.verificar(novosDados.getBairro())
        ) {
            endereco.setBairro(novosDados.getBairro());
        }
        if (
            novosDados.getCidade() != null &&
            !novosDados.getCidade().isEmpty() &&
            !stringVerificador.verificar(novosDados.getCidade())
        ) {
            endereco.setCidade(novosDados.getCidade());
        }
        if (
            novosDados.getCodigoPostal() != null &&
            !novosDados.getCodigoPostal().isEmpty() &&
            !stringVerificador.verificar(novosDados.getCodigoPostal())
        ) {
            endereco.setCodigoPostal(novosDados.getCodigoPostal());
        }
        if (
            novosDados.getEstado() != null &&
            !novosDados.getEstado().isEmpty() &&
            !stringVerificador.verificar(novosDados.getEstado())
        ) {
            endereco.setEstado(novosDados.getEstado());
        }
        if (
            novosDados.getInformacoesAdicionais() != null &&
            !novosDados.getInformacoesAdicionais().isEmpty() &&
            !stringVerificador.verificar(novosDados.getInformacoesAdicionais())
        ) {
            endereco.setInformacoesAdicionais(novosDados.getInformacoesAdicionais());
        }
        if (
            novosDados.getNumero() != null &&
            !novosDados.getNumero().isEmpty() &&
            !stringVerificador.verificar(novosDados.getNumero())
        ) {
            endereco.setNumero(novosDados.getNumero());
        }
        if (
            novosDados.getRua() != null &&
            !novosDados.getRua().isEmpty() &&
            !stringVerificador.verificar(novosDados.getRua())
        ) {
            endereco.setRua(novosDados.getRua());
        }
        
        return enderecoRepositorio.save(endereco);
    }
}
