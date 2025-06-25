package com.autobots.app.repositorios.mercadoria;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.app.entidades.UsuarioEstoque;
import com.autobots.app.entidades.UsuarioEstoqueId;

public interface UsuarioEstoqueRepositorio extends JpaRepository<UsuarioEstoque, UsuarioEstoqueId> {
    Optional<UsuarioEstoque> findByUsuarioIdAndMercadoriaId(Long usuarioId, Long mercadoriaId);
    void deleteAllByMercadoriaId(Long mercadoriaId);
    void deleteAllByUsuarioId(Long usuarioId);
}
