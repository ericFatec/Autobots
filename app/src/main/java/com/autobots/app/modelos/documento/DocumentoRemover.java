package com.autobots.app.modelos.documento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Documento;
import com.autobots.app.entidades.Usuario;
import com.autobots.app.repositorios.DocumentoRepositorio;
import com.autobots.app.repositorios.usuario.UsuarioRepositorio;

import jakarta.transaction.Transactional;

@Component
public class DocumentoRemover {
    @Autowired
    private DocumentoRepositorio documentoRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void removerDocumento(Long idDocumento) {
        Documento documento = documentoRepositorio.findById(idDocumento).get();
        Usuario usuario = documento.getUsuario();
        usuario.getDocumentos().removeIf(doc -> doc.getId().equals(idDocumento));
        usuarioRepositorio.save(usuario);
    }
}
