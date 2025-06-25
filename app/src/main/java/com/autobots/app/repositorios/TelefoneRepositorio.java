package com.autobots.app.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.app.entidades.Telefone;

public interface TelefoneRepositorio extends JpaRepository<Telefone, Long>{
    List<Telefone> findByUsuarioId(Long usuarioId);
    List<Telefone> findByEmpresaId(Long empresaId);
}
