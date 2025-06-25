package com.autobots.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.app.entidades.Documento;
import com.autobots.app.entidades.Usuario;
import com.autobots.app.modelos.documento.DocumentoAtualizar;
import com.autobots.app.modelos.documento.DocumentoRemover;
import com.autobots.app.repositorios.DocumentoRepositorio;
import com.autobots.app.types.dtos.DocumentoDTO;
import com.autobots.app.types.dtos.DocumentoReturnDTO;
import com.autobots.app.types.dtos.RequesterDTO;
import com.autobots.app.types.interfaces.CrudInterface;
import com.autobots.app.utils.CrudPermissionChecker;
import com.autobots.app.utils.mappers.DocumentoMapper;

import jakarta.transaction.Transactional;

@Service
public class DocumentoCrud implements CrudInterface<DocumentoReturnDTO, Long, DocumentoDTO, RequesterDTO> {
    @Autowired
    private DocumentoRepositorio documentoRepositorio;

    @Autowired
    private DocumentoAtualizar documentoAtualizar;
    @Autowired
    private DocumentoRemover documentoRemover;

    @Autowired
    private DocumentoMapper documentoMapper;

    @Autowired
    private CrudPermissionChecker permissionCheck;

    @Override
    @Transactional
    public DocumentoReturnDTO selecionar(Long id, RequesterDTO requester) {
        Documento documento = documentoRepositorio.findById(id).get();
        Usuario usuarioAlvo = documento.getUsuario();

        permissionCheck.checkUserAccess(usuarioAlvo, requester);

        return documentoMapper.toReturnDTO(documento);
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
    public List<DocumentoReturnDTO> selecionarFromUsuario(Long id, RequesterDTO requester) {
        List<Documento> documentos = documentoRepositorio.findByUsuarioId(id);
        Usuario usuarioAlvo = documentos.get(0).getUsuario();

        permissionCheck.checkUserAccess(usuarioAlvo, requester);

        return documentos.stream()
            .map(documentoMapper::toReturnDTO)
            .toList();
    }

    @Override
    @Transactional
    public DocumentoReturnDTO atualizar(Long id, DocumentoDTO novosDados, RequesterDTO requester) {
        Documento documentoAlvo = documentoRepositorio.findById(id).get();
        Usuario usuarioAlvo = documentoAlvo.getUsuario();

        permissionCheck.checkUserAccess(usuarioAlvo, requester);

        DocumentoReturnDTO documentoReturn = documentoMapper.toReturnDTO(
            documentoAtualizar.atualizar(documentoAlvo, novosDados)
        );
        return documentoReturn;
    }

    @Override
    public void deletar(Long id, RequesterDTO requester) {
        Documento documentoAlvo = documentoRepositorio.findById(id).get();
        Usuario usuarioAlvo = documentoAlvo.getUsuario();

        permissionCheck.checkUserAccess(usuarioAlvo, requester);

        documentoRemover.removerDocumento(documentoAlvo);
    }
}
