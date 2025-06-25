package com.autobots.app.modelos.mercadoria;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.entidades.EmpresaEstoque;
import com.autobots.app.entidades.Mercadoria;
import com.autobots.app.repositorios.mercadoria.EmpresaEstoqueRepositorio;
import com.autobots.app.repositorios.mercadoria.MercadoriaRepositorio;
import com.autobots.app.types.dtos.MercadoriaDTO;
import com.autobots.app.utils.mappers.MercadoriaMapper;

import jakarta.transaction.Transactional;

@Component
public class EmpresaInserirMercadoria {
    @Autowired
    private MercadoriaRepositorio mercadoriaRepositorio;
    @Autowired
    private EmpresaEstoqueRepositorio empresaEstoqueRepositorio;

    @Autowired
    private MercadoriaMapper mercadoriaMapper;

    @Transactional
    public Mercadoria inserirMercadoria(Empresa empresa, MercadoriaDTO mercadoriaDTO) {
        Mercadoria mercadoria = mercadoriaMapper.toObject(mercadoriaDTO);
        mercadoria.setEmpresa(empresa);
        mercadoria.setDataCadastro(Instant.now());
        Mercadoria novaMercadoria = mercadoriaRepositorio.save(mercadoria);

        EmpresaEstoque empresaEstoque = new EmpresaEstoque();
        empresaEstoque.setEmpresa(empresa);
        empresaEstoque.setMercadoria(novaMercadoria);
        empresaEstoque.setQuantidade(mercadoriaDTO.getQuantidade() != null ? mercadoriaDTO.getQuantidade() : 0);
        empresaEstoqueRepositorio.save(empresaEstoque);

        return novaMercadoria;
    }
}
