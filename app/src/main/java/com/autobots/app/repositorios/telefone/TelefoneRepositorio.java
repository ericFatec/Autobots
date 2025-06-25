package com.autobots.app.repositorios.telefone;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.app.entidades.Telefone;

public interface TelefoneRepositorio extends JpaRepository<Telefone, Long>{
    
}
