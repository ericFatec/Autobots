package com.autobots.app.repositorios.documento;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.app.entidades.Documento;

public interface DocumentoRepositorio extends JpaRepository<Documento, Long>{
    
}
