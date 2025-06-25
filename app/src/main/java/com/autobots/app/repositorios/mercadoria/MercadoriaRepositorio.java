package com.autobots.app.repositorios.mercadoria;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.app.entidades.Mercadoria;

public interface MercadoriaRepositorio extends JpaRepository<Mercadoria, Long>{
    List<Mercadoria> findByEmpresaId(Long empresaId);
    List<Mercadoria> findByUsuarioId(Long usuarioId);
}
