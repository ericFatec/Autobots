package com.autobots.app.modelos.venda;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.entidades.Mercadoria;
import com.autobots.app.entidades.Servico;
import com.autobots.app.entidades.Usuario;
import com.autobots.app.entidades.Veiculo;
import com.autobots.app.entidades.Vendas;
import com.autobots.app.entidades.snapshots.MercadoriaSnapshot;
import com.autobots.app.entidades.snapshots.ServicoSnapshot;
import com.autobots.app.entidades.snapshots.VeiculoSnapshot;
import com.autobots.app.repositorios.ServicoRepositorio;
import com.autobots.app.repositorios.VeiculoRepositorio;
import com.autobots.app.repositorios.VendaRepositorio;
import com.autobots.app.repositorios.mercadoria.MercadoriaRepositorio;
import com.autobots.app.repositorios.usuario.UsuarioRepositorio;
import com.autobots.app.types.dtos.VendaDTO;
import com.autobots.app.utils.mappers.MercadoriaMapper;
import com.autobots.app.utils.mappers.ServicoMapper;
import com.autobots.app.utils.mappers.VeiculoMapper;

import jakarta.transaction.Transactional;

@Component
public class VendaInserir {

    @Autowired
    private VendaRepositorio vendaRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private MercadoriaRepositorio mercadoriaRepositorio;
    @Autowired
    private ServicoRepositorio servicoRepositorio;
    @Autowired
    private VeiculoRepositorio veiculoRepositorio;

    @Autowired
    private MercadoriaMapper mercadoriaMapper;
    @Autowired
    private ServicoMapper servicoMapper;
    @Autowired
    private VeiculoMapper veiculoMapper;
    
    @Transactional
    public Vendas inserir(VendaDTO novaVenda) {
        Vendas venda = new Vendas();
        Usuario cliente = usuarioRepositorio.findById(novaVenda.getClienteId()).get();
        Usuario vendedor = usuarioRepositorio.findById(novaVenda.getVendedorId()).get();
        venda.setDataCadastro(Instant.now());
        venda.setIdentificacao(novaVenda.getIdentificacao());
        venda.setValorTotal(0.0);
        venda.setCliente(cliente);
        venda.setVendedor(vendedor);

        if (novaVenda.getMercadorias() != null && !novaVenda.getMercadorias().isEmpty()) {
            Set<Mercadoria> usuarioMercadorias = vendedor.getMercadorias(); // example method
            Set<Mercadoria> empresaMercadorias = vendedor.getEmpresa() != null ? vendedor.getEmpresa().getMercadorias() : null;
            for (Map.Entry<Long, Integer> entry : novaVenda.getMercadorias().entrySet()) {
                Long mercadoriaId = entry.getKey();
                Integer quantidade = entry.getValue();
                Mercadoria mercadoria =  mercadoriaRepositorio.findById(mercadoriaId).get();

                boolean mercadoriaInUsuario = usuarioMercadorias != null && usuarioMercadorias.stream()
                    .anyMatch(m -> m.getId().equals(mercadoriaId));

                boolean mercadoriaInEmpresa = empresaMercadorias != null && empresaMercadorias.stream()
                    .anyMatch(m -> m.getId().equals(mercadoriaId));

                if (!mercadoriaInUsuario && !mercadoriaInEmpresa) {
                    throw new IllegalArgumentException("Mercadoria " + mercadoria.getNome() + " não pertence ao usuário ou empresa");
                }

                MercadoriaSnapshot mercadoriaSnapshot = mercadoriaMapper.toSnapshot(mercadoria);
                mercadoriaSnapshot.setQuantidade(quantidade);
                venda.setValorTotal(
                    venda.getValorTotal() + (mercadoriaSnapshot.getValor() * quantidade)
                );
                venda.getMercadorias().add(mercadoriaSnapshot);
            }
        }

        if (novaVenda.getServicos() != null && !novaVenda.getServicos().isEmpty()) {
            Empresa empresa = vendedor.getEmpresa();
            if (empresa == null) {
                throw new IllegalArgumentException("Vendedor não pertence a uma empresa");
            }
            Set<Servico> servicos = empresa.getServicos();
            for (Long id : novaVenda.getServicos()) {
                Servico servico = servicoRepositorio.findById(id).get();
                boolean servicoInEmpresa = servicos != null && servicos.stream()
                    .anyMatch(s -> s.getId().equals(id));
                if (!servicoInEmpresa) {
                    throw new IllegalArgumentException("Serviço " + servico.getNome() + " não pertence à empresa do usuário");
                }
                ServicoSnapshot servicoSnapshot = servicoMapper.toSnapshot(servico);
                venda.setValorTotal(
                    venda.getValorTotal() + servicoSnapshot.getValor()
                );
                venda.getServicos().add(servicoSnapshot);
            }
        }

        if (novaVenda.getVeiculoId() != null) {
            Veiculo veiculo = veiculoRepositorio.findById(novaVenda.getVeiculoId()).get();
            if (!veiculo.getUsuarioId().equals(cliente.getId())) {
                throw new IllegalArgumentException("Veículo não pertence ao cliente");
            }
            VeiculoSnapshot veiculoSnapshot = veiculoMapper.toSnapshot(veiculo);
            venda.setVeiculo(veiculoSnapshot);
        }

        return vendaRepositorio.save(venda);
    }
}
