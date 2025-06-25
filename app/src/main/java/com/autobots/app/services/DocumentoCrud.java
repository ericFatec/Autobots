package com.autobots.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.app.entidades.Documento;
import com.autobots.app.modelos.documento.DocumentoAtualizar;
import com.autobots.app.modelos.documento.DocumentoRemover;
import com.autobots.app.repositorios.DocumentoRepositorio;
import com.autobots.app.types.dtos.DocumentoDTO;
import com.autobots.app.types.dtos.DocumentoReturnDTO;
import com.autobots.app.types.interfaces.CrudInterface;
import com.autobots.app.utils.mappers.DocumentoMapper;

import jakarta.transaction.Transactional;

@Service
public class DocumentoCrud implements CrudInterface<DocumentoReturnDTO, Long, DocumentoDTO> {
    @Autowired
    private DocumentoRepositorio documentoRepositorio;

    @Autowired
    private DocumentoAtualizar documentoAtualizar;
    @Autowired
    private DocumentoRemover documentoRemover;

    @Autowired
    private DocumentoMapper documentoMapper;

    @Override
    @Transactional
    public DocumentoReturnDTO selecionar(Long id) {
        DocumentoReturnDTO documentoReturn = documentoMapper.toReturnDTO(
            documentoRepositorio.findById(id).get()
        );
        return documentoReturn;
    }

    @Override
    @Transactional
    public List<DocumentoReturnDTO> selecionarTodos() {
        List<Documento> documentos = documentoRepositorio.findAll();
        return documentos.stream()
            .map(documentoMapper::toReturnDTO)
            .toList();
    }

    @Transactional
    public List<DocumentoReturnDTO> selecionarFromUsuario(Long id) {
        List<Documento> documentos = documentoRepositorio.findByUsuarioId(id);
        return documentos.stream()
            .map(documentoMapper::toReturnDTO)
            .toList();
    }

    @Override
    @Transactional
    public DocumentoReturnDTO atualizar(Long id, DocumentoDTO novosDados) {
        Documento documentoAlvo = documentoRepositorio.findById(id).get();
        DocumentoReturnDTO documentoReturn = documentoMapper.toReturnDTO(
            documentoAtualizar.atualizar(documentoAlvo, novosDados)
        );
        return documentoReturn;
    }

    @Override
    public void deletar(Long id) {
        documentoRemover.removerDocumento(id);
    }
}
