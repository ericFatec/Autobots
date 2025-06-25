package com.autobots.app.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.autobots.app.entidades.Empresa;
import com.autobots.app.types.dtos.EmpresaCadastroDTO;
import com.autobots.app.types.dtos.EmpresaDTO;
import com.autobots.app.types.dtos.EmpresaReturnDTO;
import com.autobots.app.utils.validators.StringVerificador;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = { TelefoneMapper.class, EnderecoMapper.class, StringVerificador.class }
)
public interface EmpresaMapper {
    @Mapping(target = "razaoSocial", qualifiedByName = "mapIfValidString")
    @Mapping(target = "nomeFantasia", qualifiedByName = "mapIfValidString")
    @Mapping(target = "telefones", ignore = true)
    Empresa toObject(EmpresaCadastroDTO empresaCadastroDTO);
    EmpresaReturnDTO toReturnDTO(Empresa empresa);

    @Mapping(target = "razaoSocial", qualifiedByName = "mapIfValidString")
    @Mapping(target = "nomeFantasia", qualifiedByName = "mapIfValidString")
    void updateEmpresa(EmpresaDTO dto, @MappingTarget Empresa empresa);
}
