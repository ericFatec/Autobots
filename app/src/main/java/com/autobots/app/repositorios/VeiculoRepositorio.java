package com.autobots.app.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.app.entidades.Veiculo;

public interface VeiculoRepositorio extends JpaRepository<Veiculo, Long>{
    List<Veiculo> findByUsuarioId(Long usuarioId);
}
