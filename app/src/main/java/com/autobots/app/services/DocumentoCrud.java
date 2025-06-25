package com.autobots.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.app.entidades.Documento;
import com.autobots.app.modelos.documento.DocumentoAtualizar;
import com.autobots.app.repositorios.documento.DocumentoRepositorio;
import com.autobots.app.types.dtos.DocumentoDTO;
import com.autobots.app.types.interfaces.CrudInterface;

@Service
public class DocumentoCrud implements CrudInterface<Documento, Long, DocumentoDTO> {
    @Autowired
    private DocumentoRepositorio documentoRepositorio;

    @Autowired
    private DocumentoAtualizar documentoAtualizar;

    public Documento inserir(Documento documento) {
        return documentoRepositorio.save(documento);
    }

    public Documento selecionar(Long id) {
        return documentoRepositorio.findById(id).get();
    }

    public List<Documento> selecionarTodos() {
        return documentoRepositorio.findAll();
    }

    public Documento atualizar(Long id, DocumentoDTO novosDados) {
        Documento documentoAlvo = documentoRepositorio.findById(id).get();
        return documentoAtualizar.atualizar(documentoAlvo, novosDados);
    }

    public void deletar(Long id) {
        documentoRepositorio.deleteById(id);
    }
}
