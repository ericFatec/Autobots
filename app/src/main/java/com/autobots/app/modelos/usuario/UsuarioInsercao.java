package com.autobots.app.modelos.usuario;

import java.time.Instant;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.app.entidades.Usuario;
import com.autobots.app.exceptions.MissingDocumentException;
import com.autobots.app.modelos.credencial.UsuarioCredencialInserir;
import com.autobots.app.modelos.documento.UsuarioInserirDocumento;
import com.autobots.app.modelos.telefone.UsuarioInserirTelefone;
import com.autobots.app.repositorios.usuario.UsuarioRepositorio;
import com.autobots.app.types.dtos.CredencialDTO;
import com.autobots.app.types.dtos.DocumentoDTO;
import com.autobots.app.types.dtos.TelefoneDTO;
import com.autobots.app.types.dtos.UsuarioCadastroDTO;
import com.autobots.app.types.enums.TipoCredencial;
import com.autobots.app.utils.mappers.UsuarioMapper;

import jakarta.transaction.Transactional;

@Component
public class UsuarioInsercao {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private UsuarioInserirDocumento usuarioInserirDocumento;
    @Autowired
    private UsuarioInserirTelefone usuarioInserirTelefone;
    @Autowired
    private UsuarioCredencialInserir usuarioCredencialInserir;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Transactional
    public Usuario inserir(UsuarioCadastroDTO usuarioCadastroDTO) {
        Set<DocumentoDTO> documentos = usuarioCadastroDTO.getDocumentos();
        Set<TelefoneDTO> telefones = usuarioCadastroDTO.getTelefones();

        Usuario usuario = usuarioMapper.toObject(usuarioCadastroDTO);

        usuario.setDataCadastro(Instant.now());

        Usuario usuarioNovo = usuarioRepositorio.save(usuario);

        if (documentos != null && !documentos.isEmpty()) {
            for (DocumentoDTO doc : documentos) {
                usuarioInserirDocumento.inserirDocumento(usuarioNovo, doc);
            }
        } else {
            throw new MissingDocumentException("Usuário precisa ter pelo menos um documento");
        }
        if (telefones != null && !telefones.isEmpty()) {
            for (TelefoneDTO tel : telefones) {
                usuarioInserirTelefone.inserirTelefone(usuarioNovo, tel);
            }
        }
        Usuario usuarioCompleto = usuarioRepositorio.findById(usuarioNovo.getId()).get();
        CredencialDTO novaCredencial = new CredencialDTO();
        novaCredencial.setTipo(TipoCredencial.SENHA);
        novaCredencial.setLogin(
            usuarioCadastroDTO.getLogin() != null ?
            usuarioCadastroDTO.getLogin() :
            usuarioCompleto.getNome()+usuarioCompleto.getId().toString()
        );
        novaCredencial.setSenha(usuarioCadastroDTO.getSenha());
        usuarioCredencialInserir.inserirCredencial(usuarioCompleto, novaCredencial);
        return usuarioCompleto;
    }
}
