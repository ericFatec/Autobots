package com.autobots.app.repositorios.cliente;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.autobots.app.entidades.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long>{
    @Query("SELECT e.id FROM Cliente c JOIN c.endereco e WHERE c.id = :id")
    Long findEnderecoIdByClienteId(@Param("id") Long id);

    @Query("SELECT c.id FROM Cliente c JOIN c.endereco e WHERE e.id = :enderecoId")
    Long findClienteIdByEnderecoId(@Param("enderecoId") Long enderecoId);

    @Query("SELECT c FROM Cliente c JOIN c.telefones t WHERE t.id = :telefoneId")
    Cliente findByTelefoneId(@Param("telefoneId") Long telefoneId);

    @Query("SELECT c FROM Cliente c JOIN c.documentos d WHERE d.id = :documentoId")
    Cliente findByDocumentoId(@Param("documentoId") Long documentoId);

    @Query("SELECT c.id FROM Cliente c JOIN c.telefones t WHERE t.id = :telefoneId")
    Long findClienteIdByTelefoneId(@Param("telefoneId") Long telefoneId);

    @Query("SELECT c.id FROM Cliente c JOIN c.documentos d WHERE d.id = :documentoId")
    Long findClienteIdByDocumentoId(@Param("documentoId") Long documentoId);
}
