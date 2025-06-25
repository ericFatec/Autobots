package com.autobots.app.repositorios.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.app.entidades.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{
    @EntityGraph(attributePaths = { "endereco", "documentos", "telefones" })
    List<Usuario> findByEmpresaId(Long empresaId);

    @EntityGraph(attributePaths = { "endereco", "documentos", "telefones" })
    List<Usuario> findAll();

    @EntityGraph(attributePaths = { "endereco", "documentos", "telefones" })
    Optional<Usuario> findById(Long id);
}
