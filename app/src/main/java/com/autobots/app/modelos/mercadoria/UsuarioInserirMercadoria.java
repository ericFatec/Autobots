package com.autobots.app.modelos.mercadoria;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Mercadoria;
import com.autobots.app.entidades.Usuario;
import com.autobots.app.entidades.UsuarioEstoque;
import com.autobots.app.repositorios.mercadoria.MercadoriaRepositorio;
import com.autobots.app.repositorios.mercadoria.UsuarioEstoqueRepositorio;
import com.autobots.app.types.dtos.MercadoriaDTO;
import com.autobots.app.utils.mappers.MercadoriaMapper;

import jakarta.transaction.Transactional;

@Component
public class UsuarioInserirMercadoria {
    @Autowired
    private MercadoriaRepositorio mercadoriaRepositorio;
    @Autowired
    private UsuarioEstoqueRepositorio usuarioEstoqueRepositorio;

    @Autowired
    private MercadoriaMapper mercadoriaMapper;

    @Transactional
    public Mercadoria inserirMercadoria(Usuario usuario, MercadoriaDTO mercadoriaDTO) {
        Mercadoria mercadoria = mercadoriaMapper.toObject(mercadoriaDTO);
        mercadoria.setUsuario(usuario);
        mercadoria.setDataCadastro(Instant.now());
        Mercadoria novaMercadoria = mercadoriaRepositorio.save(mercadoria);

        UsuarioEstoque usuarioEstoque = new UsuarioEstoque();
        usuarioEstoque.setUsuario(usuario);
        usuarioEstoque.setMercadoria(novaMercadoria);
        usuarioEstoque.setQuantidade(mercadoriaDTO.getQuantidade() != null ? mercadoriaDTO.getQuantidade() : 0);
        usuarioEstoqueRepositorio.save(usuarioEstoque);

        return novaMercadoria;
    }
}
