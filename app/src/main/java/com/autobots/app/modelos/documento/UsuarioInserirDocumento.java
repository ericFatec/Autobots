package com.autobots.app.modelos.documento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Usuario;
import com.autobots.app.repositorios.DocumentoRepositorio;
import com.autobots.app.types.dtos.DocumentoDTO;
import com.autobots.app.utils.mappers.DocumentoMapper;
import com.autobots.app.entidades.Documento;

import jakarta.transaction.Transactional;

@Component
public class UsuarioInserirDocumento {

    @Autowired
    private DocumentoRepositorio documentoRepositorio;

    @Autowired
    private DocumentoMapper documentoMapper;

    @Transactional
    public Documento inserirDocumento(Usuario usuario, DocumentoDTO documentoDTO) {
        Documento documento = documentoMapper.toObject(documentoDTO);
        documento.setUsuario(usuario);
        return documentoRepositorio.save(documento);
    }
}
