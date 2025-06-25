package com.autobots.app.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.autobots.app.entidades.Credencial;
import com.autobots.app.entidades.CredencialCodigoDeBarras;
import com.autobots.app.entidades.CredencialUsuarioSenha;
import com.autobots.app.types.dtos.CredencialReturnDTO;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CredencialMapper {
    @Mapping(target = "tipo", constant = "CODIGO")
    @Mapping(target = "codigo", source = "codigo")
    CredencialReturnDTO toReturnDTO(CredencialCodigoDeBarras credencial);

    @Mapping(target = "tipo", constant = "SENHA")
    @Mapping(target = "login", source = "login")
    @Mapping(target = "senha", source = "senha")
    CredencialReturnDTO toReturnDTO(CredencialUsuarioSenha credencial);

    default CredencialReturnDTO toReturnDTOPolymorphic(Credencial entity) {
        if (entity instanceof CredencialUsuarioSenha credencialSenha) {
            return toReturnDTO(credencialSenha);
        }
        if (entity instanceof CredencialCodigoDeBarras credencialCodigo) {
            return toReturnDTO(credencialCodigo);
        }
        return null;
    }
}
