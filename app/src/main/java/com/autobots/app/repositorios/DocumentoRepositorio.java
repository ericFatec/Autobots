package com.autobots.app.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.app.entidades.Documento;

public interface DocumentoRepositorio extends JpaRepository<Documento, Long>{
    List<Documento> findByUsuarioId(Long usuarioId);
}
