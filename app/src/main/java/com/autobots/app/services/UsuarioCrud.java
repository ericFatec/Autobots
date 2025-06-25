package com.autobots.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.entidades.Usuario;
import com.autobots.app.modelos.credencial.UsuarioCredencialInserir;
import com.autobots.app.modelos.documento.UsuarioInserirDocumento;
import com.autobots.app.modelos.mercadoria.UsuarioInserirMercadoria;
import com.autobots.app.modelos.telefone.UsuarioInserirTelefone;
import com.autobots.app.modelos.usuario.UsuarioAtualizar;
import com.autobots.app.modelos.usuario.UsuarioInsercao;
import com.autobots.app.modelos.usuario.UsuarioRemover;
import com.autobots.app.modelos.veiculo.UsuarioInserirVeiculo;
import com.autobots.app.repositorios.usuario.UsuarioRepositorio;
import com.autobots.app.types.dtos.CredencialDTO;
import com.autobots.app.types.dtos.CredencialReturnDTO;
import com.autobots.app.types.dtos.DocumentoDTO;
import com.autobots.app.types.dtos.DocumentoReturnDTO;
import com.autobots.app.types.dtos.MercadoriaDTO;
import com.autobots.app.types.dtos.MercadoriaReturnDTO;
import com.autobots.app.types.dtos.RequesterDTO;
import com.autobots.app.types.dtos.TelefoneDTO;
import com.autobots.app.types.dtos.TelefoneReturnDTO;
import com.autobots.app.types.dtos.UsuarioAtualizarDTO;
import com.autobots.app.types.dtos.UsuarioCadastroDTO;
import com.autobots.app.types.dtos.UsuarioReturnDTO;
import com.autobots.app.types.dtos.VeiculoDTO;
import com.autobots.app.types.dtos.VeiculoReturnDTO;
import com.autobots.app.types.enums.TipoCredencial;
import com.autobots.app.types.interfaces.CrudInterfaceWithCadastro;
import com.autobots.app.utils.BarcodeGenerator;
import com.autobots.app.utils.CrudPermissionChecker;
import com.autobots.app.utils.PerfilPrioridade;
import com.autobots.app.utils.mappers.CredencialMapper;
import com.autobots.app.utils.mappers.DocumentoMapper;
import com.autobots.app.utils.mappers.MercadoriaMapper;
import com.autobots.app.utils.mappers.TelefoneMapper;
import com.autobots.app.utils.mappers.UsuarioMapper;
import com.autobots.app.utils.mappers.VeiculoMapper;

import jakarta.transaction.Transactional;

@Service
public class UsuarioCrud implements CrudInterfaceWithCadastro<UsuarioReturnDTO, Long, UsuarioCadastroDTO, UsuarioAtualizarDTO, RequesterDTO> {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private UsuarioInsercao usuarioInsercao;
    @Autowired
    private UsuarioAtualizar usuarioAtualizar;
    @Autowired
    private UsuarioCredencialInserir usuarioCredencialInserir;
    @Autowired
    private UsuarioInserirDocumento usuarioInserirDocumento;
    @Autowired
    private UsuarioInserirTelefone usuarioInserirTelefone;
    @Autowired
    private UsuarioInserirMercadoria usuarioInserirMercadoria;
    @Autowired
    private UsuarioInserirVeiculo usuarioInserirVeiculo;
    @Autowired
    private UsuarioRemover usuarioRemover;

    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private CredencialMapper credencialMapper;
    @Autowired
    private DocumentoMapper documentoMapper;
    @Autowired
    private TelefoneMapper telefoneMapper;
    @Autowired
    private MercadoriaMapper mercadoriaMapper;
    @Autowired
    private VeiculoMapper veiculoMapper;

    @Autowired
    private CrudPermissionChecker permissionChecker;

    @Override
    @Transactional
    public UsuarioReturnDTO inserir(UsuarioCadastroDTO usuario, RequesterDTO requester) {
        permissionChecker.canRegisterRole(usuario, requester);
        return usuarioMapper.toReturnDTO(usuarioInsercao.inserir(usuario));
    }

    @Override
    @Transactional
    public UsuarioReturnDTO selecionar(Long id, RequesterDTO requester) {
        Usuario usuario = usuarioRepositorio.findById(id).get();
        permissionChecker.checkUserAccess(usuario, requester);
        return usuarioMapper.toReturnDTO(usuario);
    }

    @Override
    @Transactional
    public List<UsuarioReturnDTO> selecionarTodos() {
        return usuarioRepositorio.findAll()
            .stream()
            .map(usuarioMapper::toReturnDTO)
            .toList();
    }

    @Transactional
    public List<UsuarioReturnDTO> selecionarFromEmpresa(Long id, RequesterDTO requester) {
        List<Usuario> usuarios = usuarioRepositorio.findByEmpresaId(id);
        Empresa empresa = usuarios.get(0).getEmpresa();
        
        permissionChecker.isFromEmpresa(empresa, requester);

        return usuarios
            .stream()
            .map(usuarioMapper::toReturnDTO)
            .toList();
    } 

    @Override
    @Transactional
    public UsuarioReturnDTO atualizar(Long id, UsuarioAtualizarDTO novosDados, RequesterDTO requester) {
        Usuario usuarioAlvo = usuarioRepositorio.findById(id).get();
        permissionChecker.checkUserAccess(usuarioAlvo, requester);
        return usuarioMapper.toReturnDTO(
            usuarioAtualizar.atualizar(usuarioAlvo, novosDados)
        );
    }

    @Transactional
    public CredencialReturnDTO inserirCredencial(Long id, CredencialDTO credencial, RequesterDTO requester) {
        Usuario usuarioAlvo = usuarioRepositorio.findById(id).get();

        permissionChecker.checkUserAccess(usuarioAlvo, requester);

        // Se estiver cadastrando um código de barras, gere um código
        if (credencial.getTipo().equals(TipoCredencial.CODIGO)) {
            String codigo = BarcodeGenerator.generateCodigo(
                                PerfilPrioridade.highestPerfil(usuarioAlvo));
            credencial.setCodigo(codigo);
        }

        CredencialReturnDTO novaCredencial = credencialMapper.toReturnDTOPolymorphic(
            usuarioCredencialInserir.inserirCredencial(usuarioAlvo, credencial)
        );
        return novaCredencial;
    }

    @Transactional
    public DocumentoReturnDTO inserirDocumento(Long id, DocumentoDTO documento, RequesterDTO requester) {
        Usuario usuarioAlvo = usuarioRepositorio.findById(id).get();
        permissionChecker.checkUserAccess(usuarioAlvo, requester);
        return documentoMapper.toReturnDTO(
            usuarioInserirDocumento.inserirDocumento(usuarioAlvo, documento)
        );
    }

    @Transactional
    public TelefoneReturnDTO inserirTelefone(Long id, TelefoneDTO telefone, RequesterDTO requester) {
        Usuario usuarioAlvo = usuarioRepositorio.findById(id).get();
        permissionChecker.checkUserAccess(usuarioAlvo, requester);
        return telefoneMapper.toReturnDTO(
            usuarioInserirTelefone.inserirTelefone(usuarioAlvo, telefone)
        );
    }

    @Transactional
    public MercadoriaReturnDTO inserirMercadoria(Long id, MercadoriaDTO mercadoriaDTO, RequesterDTO requester) {
        Usuario usuarioAlvo = usuarioRepositorio.findById(id).get();
        permissionChecker.checkUserAccess(usuarioAlvo, requester);
        return mercadoriaMapper.toReturnDTO(
            usuarioInserirMercadoria.inserirMercadoria(usuarioAlvo, mercadoriaDTO)
        );
    }

    @Transactional
    public VeiculoReturnDTO inserirVeiculo(Long id, VeiculoDTO veiculoDTO, RequesterDTO requester) {
        Usuario usuarioAlvo = usuarioRepositorio.findById(id).get();
        permissionChecker.checkUserAccess(usuarioAlvo, requester);
        return veiculoMapper.toReturnDTO(
            usuarioInserirVeiculo.inserirVeiculo(usuarioAlvo, veiculoDTO)
        );
    }

    @Override
    public void deletar(Long id, RequesterDTO requester) {
        Usuario usuario = usuarioRepositorio.findById(id).get();
        permissionChecker.checkUserAccess(usuario, requester);
        usuarioRemover.removerUsuario(usuario);
    }
}
