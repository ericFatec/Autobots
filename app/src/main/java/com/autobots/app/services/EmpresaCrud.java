package com.autobots.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.modelos.empresa.EmpresaAtualizar;
import com.autobots.app.modelos.empresa.EmpresaInsercao;
import com.autobots.app.modelos.empresa.EmpresaRemover;
import com.autobots.app.modelos.mercadoria.EmpresaInserirMercadoria;
import com.autobots.app.modelos.servico.EmpresaInserirServico;
import com.autobots.app.modelos.telefone.EmpresaInserirTelefone;
import com.autobots.app.modelos.usuario.EmpresaInserirUsuario;
import com.autobots.app.repositorios.EmpresaRepositorio;
import com.autobots.app.types.dtos.EmpresaCadastroDTO;
import com.autobots.app.types.dtos.EmpresaDTO;
import com.autobots.app.types.dtos.EmpresaReturnDTO;
import com.autobots.app.types.dtos.MercadoriaDTO;
import com.autobots.app.types.dtos.MercadoriaReturnDTO;
import com.autobots.app.types.dtos.ServicoDTO;
import com.autobots.app.types.dtos.ServicoReturnDTO;
import com.autobots.app.types.dtos.TelefoneDTO;
import com.autobots.app.types.dtos.TelefoneReturnDTO;
import com.autobots.app.types.dtos.UsuarioCadastroDTO;
import com.autobots.app.types.dtos.UsuarioReturnDTO;
import com.autobots.app.types.interfaces.CrudInterfaceWithCadastro;
import com.autobots.app.utils.mappers.EmpresaMapper;
import com.autobots.app.utils.mappers.MercadoriaMapper;
import com.autobots.app.utils.mappers.ServicoMapper;
import com.autobots.app.utils.mappers.TelefoneMapper;
import com.autobots.app.utils.mappers.UsuarioMapper;

import jakarta.transaction.Transactional;

@Service
public class EmpresaCrud implements CrudInterfaceWithCadastro<EmpresaReturnDTO, Long, EmpresaCadastroDTO, EmpresaDTO>{
    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @Autowired
    private EmpresaInsercao empresaInsercao;
    @Autowired
    private EmpresaAtualizar empresaAtualizar;
    @Autowired
    private EmpresaInserirTelefone empresaInserirTelefone;
    @Autowired
    private EmpresaInserirUsuario empresaInserirUsuario;
    @Autowired
    private EmpresaInserirMercadoria empresaInserirMercadoria;
    @Autowired
    private EmpresaInserirServico empresaInserirServico;
    @Autowired
    private EmpresaRemover empresaRemover;

    @Autowired
    private EmpresaMapper empresaMapper;
    @Autowired
    private TelefoneMapper telefoneMapper;
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private MercadoriaMapper mercadoriaMapper;
    @Autowired
    private ServicoMapper servicoMapper;
    
    @Override
    @Transactional
    public EmpresaReturnDTO inserir(EmpresaCadastroDTO empresaCadastroDTO) {
        return empresaMapper.toReturnDTO(
            empresaInsercao.inserir(empresaCadastroDTO)
        );
    }

    @Override
    @Transactional
    public EmpresaReturnDTO selecionar(Long id) {
        return empresaMapper.toReturnDTO(
            empresaRepositorio.findById(id).get()
        );
    }

    @Override
    @Transactional
    public List<EmpresaReturnDTO> selecionarTodos() {
        return empresaRepositorio.findAll()
            .stream()
            .map(empresaMapper::toReturnDTO)
            .toList();
    }

    @Override
    @Transactional
    public EmpresaReturnDTO atualizar(Long id, EmpresaDTO novosDados) {
        Empresa empresaAlvo = empresaRepositorio.findById(id).get();
        Empresa empresaLoaded = empresaAtualizar.atualizar(empresaAlvo, novosDados);
        return empresaMapper.toReturnDTO(empresaLoaded);
    }

    @Transactional
    public TelefoneReturnDTO inserirTelefone(Long id, TelefoneDTO telefoneDTO) {
        Empresa empresaAlvo = empresaRepositorio.findById(id).get();
        return telefoneMapper.toReturnDTO(
            empresaInserirTelefone.inserirTelefone(empresaAlvo, telefoneDTO)
        );
    }

    @Transactional
    public UsuarioReturnDTO inserirUsuario(Long id, UsuarioCadastroDTO usuarioCadastroDTO) {
        Empresa empresaAlvo = empresaRepositorio.findById(id).get();
        return usuarioMapper.toReturnDTO(
            empresaInserirUsuario.inserirUsuario(empresaAlvo, usuarioCadastroDTO)
        );
    }

    @Transactional
    public MercadoriaReturnDTO inserirMercadoria(Long id, MercadoriaDTO mercadoriaDTO) {
        Empresa empresaAlvo = empresaRepositorio.findById(id).get();
        return mercadoriaMapper.toReturnDTO(
            empresaInserirMercadoria.inserirMercadoria(empresaAlvo, mercadoriaDTO)
        );
    }

    @Transactional
    public ServicoReturnDTO inserirServico(Long id, ServicoDTO servicoDTO) {
        Empresa empresaAlvo = empresaRepositorio.findById(id).get();
        return servicoMapper.toReturnDTO(
            empresaInserirServico.inserirServico(empresaAlvo, servicoDTO)
        );
    }

    @Override
    public void deletar(Long id) {
        empresaRemover.removerEmpresa(id);
    }
}
