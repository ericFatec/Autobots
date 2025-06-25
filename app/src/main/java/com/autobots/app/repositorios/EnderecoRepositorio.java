package com.autobots.app.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.app.entidades.Endereco;

public interface EnderecoRepositorio extends JpaRepository<Endereco, Long>{
    
}
