package com.autobots.app.modelos.documento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Documento;
import com.autobots.app.repositorios.DocumentoRepositorio;
import com.autobots.app.types.dtos.DocumentoDTO;
import com.autobots.app.utils.mappers.DocumentoMapper;

import jakarta.transaction.Transactional;

@Component
public class DocumentoAtualizar {

    @Autowired
    private DocumentoRepositorio documentoRepositorio;

    @Autowired
    private DocumentoMapper documentoMapper;

    @Transactional
    public Documento atualizar(Documento documento, DocumentoDTO novosDados) {
        documentoMapper.updateDocumento(novosDados, documento);
        return documentoRepositorio.save(documento);
    }
}
