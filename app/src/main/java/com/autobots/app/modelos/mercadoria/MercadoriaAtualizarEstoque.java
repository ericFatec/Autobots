package com.autobots.app.modelos.mercadoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.EmpresaEstoque;
import com.autobots.app.entidades.Mercadoria;
import com.autobots.app.entidades.UsuarioEstoque;
import com.autobots.app.repositorios.mercadoria.EmpresaEstoqueRepositorio;
import com.autobots.app.repositorios.mercadoria.UsuarioEstoqueRepositorio;
import com.autobots.app.types.dtos.MercadoriaEstoqueDTO;
import com.autobots.app.types.enums.TipoAtualizacaoEstoque;

@Component
public class MercadoriaAtualizarEstoque {
    @Autowired
    private EmpresaEstoqueRepositorio empresaEstoqueRepositorio;
    @Autowired
    private UsuarioEstoqueRepositorio usuarioEstoqueRepositorio;
    
    public void atualizarEstoque(Mercadoria mercadoria, MercadoriaEstoqueDTO novosDados) {
        if (mercadoria.getUsuario() != null) {
            Long usuarioId = mercadoria.getUsuarioId();
            Long mercadoriaId = mercadoria.getId();
            UsuarioEstoque estoque = usuarioEstoqueRepositorio.findByUsuarioIdAndMercadoriaId(usuarioId, mercadoriaId).get();
            Integer quantidade = estoque.getQuantidade();
            estoque.setQuantidade(realizarOperacao(quantidade, novosDados));
            usuarioEstoqueRepositorio.save(estoque);
        } else {
            Long empresaId = mercadoria.getEmpresaId();
            Long mercadoriaId = mercadoria.getId();
            EmpresaEstoque estoque = empresaEstoqueRepositorio.findByEmpresaIdAndMercadoriaId(empresaId, mercadoriaId).get();
            Integer quantidade = estoque.getQuantidade();
            estoque.setQuantidade(realizarOperacao(quantidade, novosDados));
            empresaEstoqueRepositorio.save(estoque);
        }
    }

    private Integer realizarOperacao(Integer quantidade, MercadoriaEstoqueDTO novosDados) {
        TipoAtualizacaoEstoque operacao = novosDados.getOperacao();
        return switch (operacao) {
            case ATUALIZAR -> novosDados.getQuantidade();
            case ADICIONAR -> quantidade + novosDados.getQuantidade();
            case SUBTRAIR -> quantidade - novosDados.getQuantidade();
            default -> quantidade;
        };
    }
}
