package com.autobots.app.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.app.entidades.Usuario;
import com.autobots.app.entidades.Vendas;

public interface VendaRepositorio extends JpaRepository<Vendas, Long>{
    List<Vendas> findByClienteIdOrVendedorId(Long clienteId, Long vendedorId);
    List<Vendas> findByClienteInOrVendedorIn(List<Usuario> clientes, List<Usuario> vendedores);
}
