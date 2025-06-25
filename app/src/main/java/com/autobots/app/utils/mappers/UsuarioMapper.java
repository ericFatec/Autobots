package com.autobots.app.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.autobots.app.entidades.Usuario;
import com.autobots.app.entidades.snapshots.UsuarioSnapshot;
import com.autobots.app.types.dtos.UsuarioAtualizarDTO;
import com.autobots.app.types.dtos.UsuarioCadastroDTO;
import com.autobots.app.types.dtos.UsuarioReturnDTO;
import com.autobots.app.utils.validators.StringVerificador;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = { DocumentoMapper.class, EnderecoMapper.class, TelefoneMapper.class, StringVerificador.class }
)
public interface UsuarioMapper {
    @Mapping(target = "documentos", ignore = true)
    @Mapping(target = "telefones", ignore = true)
    @Mapping(target = "nome", qualifiedByName = "mapIfValidString")
    @Mapping(target = "nomeSocial", qualifiedByName = "mapIfValidString")
    Usuario toObject(UsuarioCadastroDTO usuarioCadastroDTO);
    UsuarioReturnDTO toReturnDTO(Usuario usuario);

    @Mapping(target = "usuarioId", source = "id")
    @Mapping(target = "empresa", expression = "java(usuario.getEmpresa() != null ? usuario.getEmpresa().getRazaoSocial() : null)")
    UsuarioSnapshot toSnapshot(Usuario usuario);

    @Mapping(target = "nome", qualifiedByName = "mapIfValidString")
    @Mapping(target = "nomeSocial", qualifiedByName = "mapIfValidString")
    void updateUsuario(UsuarioAtualizarDTO dto, @MappingTarget Usuario usuario);
}