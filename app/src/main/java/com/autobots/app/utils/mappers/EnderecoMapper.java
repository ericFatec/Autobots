package com.autobots.app.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.autobots.app.entidades.Endereco;
import com.autobots.app.entidades.snapshots.EnderecoSnapshot;
import com.autobots.app.types.dtos.EnderecoDTO;
import com.autobots.app.types.dtos.EnderecoReturnDTO;
import com.autobots.app.utils.validators.StringVerificador;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = StringVerificador.class
)
public interface EnderecoMapper {
    @Mapping(target = "estado", qualifiedByName = "mapIfValidString")
    @Mapping(target = "cidade", qualifiedByName = "mapIfValidString")
    @Mapping(target = "bairro", qualifiedByName = "mapIfValidString")
    @Mapping(target = "rua", qualifiedByName = "mapIfValidString")
    @Mapping(target = "numero", qualifiedByName = "mapIfValidString")
    @Mapping(target = "codigoPostal", qualifiedByName = "mapIfValidString")
    @Mapping(target = "informacoesAdicionais", qualifiedByName = "mapIfValidString")
    Endereco toObject(EnderecoDTO enderecoDTO);
    EnderecoDTO toDTO(Endereco endereco);
    EnderecoReturnDTO toReturnDTO(Endereco endereco);
    EnderecoSnapshot toSnapshot(Endereco endereco);

    @Mapping(target = "estado", qualifiedByName = "mapIfValidString")
    @Mapping(target = "cidade", qualifiedByName = "mapIfValidString")
    @Mapping(target = "bairro", qualifiedByName = "mapIfValidString")
    @Mapping(target = "rua", qualifiedByName = "mapIfValidString")
    @Mapping(target = "numero", qualifiedByName = "mapIfValidString")
    @Mapping(target = "codigoPostal", qualifiedByName = "mapIfValidString")
    @Mapping(target = "informacoesAdicionais", qualifiedByName = "mapIfValidString")
    void updateEndereco(EnderecoDTO dto, @MappingTarget Endereco endereco);
}
