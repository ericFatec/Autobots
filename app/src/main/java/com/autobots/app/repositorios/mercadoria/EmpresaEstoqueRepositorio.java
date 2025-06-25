package com.autobots.app.repositorios.mercadoria;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.app.entidades.EmpresaEstoque;
import com.autobots.app.entidades.EmpresaEstoqueId;

public interface EmpresaEstoqueRepositorio extends JpaRepository<EmpresaEstoque, EmpresaEstoqueId>{
    Optional<EmpresaEstoque> findByEmpresaIdAndMercadoriaId(Long empresaId, Long mercadoriaId);
    void deleteAllByMercadoriaId(Long mercadoriaId);
    void deleteAllByEmpresaId(Long empresaId);
}
