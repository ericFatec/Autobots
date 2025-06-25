package com.autobots.app.modelos.documento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Documento;
import com.autobots.app.repositorios.documento.DocumentoRepositorio;
import com.autobots.app.types.dtos.DocumentoDTO;
import com.autobots.app.utils.StringVerificador;

import jakarta.annotation.PostConstruct;

@Component
public class DocumentoAtualizar {

    @Autowired
    private DocumentoRepositorio documentoRepositorio;

    private StringVerificador stringVerificador;

    @PostConstruct
    public void init() {
        this.stringVerificador = new StringVerificador();
    }

    public Documento atualizar(Documento documento, DocumentoDTO novosDados) {
        if (novosDados.getTipo() != null) {
            documento.setTipo(novosDados.getTipo());
        }
        if (
            novosDados.getNumero() != null &&
            !novosDados.getNumero().isEmpty() &&
            stringVerificador.verificar(novosDados.getNumero())
        ) {
            documento.setNumero(novosDados.getNumero());
        }

        return documentoRepositorio.save(documento);
    }
}
