package com.autobots.app.repositorios.endereco;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.app.entidades.Endereco;

public interface EnderecoRepositorio extends JpaRepository<Endereco, Long>{
    
}
