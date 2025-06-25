package com.autobots.app.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.app.entidades.Empresa;

public interface EmpresaRepositorio extends JpaRepository<Empresa, Long>{
    @EntityGraph(attributePaths = { "endereco", "telefones" })
    List<Empresa> findAll();

    @EntityGraph(attributePaths = { "endereco", "telefones" })
    Optional<Empresa> findById(Long id);
}
