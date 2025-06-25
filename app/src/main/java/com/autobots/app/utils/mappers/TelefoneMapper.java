package com.autobots.app.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.autobots.app.entidades.Telefone;
import com.autobots.app.entidades.snapshots.TelefoneSnapshot;
import com.autobots.app.types.dtos.TelefoneDTO;
import com.autobots.app.types.dtos.TelefoneReturnDTO;
import com.autobots.app.utils.validators.StringVerificador;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = StringVerificador.class
)
public interface TelefoneMapper {
    @Mapping(target = "ddd", qualifiedByName = "mapIfValidString")
    @Mapping(target = "numero", qualifiedByName = "mapIfValidString")
    Telefone toObject(TelefoneDTO telefoneDTO);
    TelefoneDTO toDTO(Telefone telefone);
    TelefoneReturnDTO toReturnDTO(Telefone telefone);
    TelefoneSnapshot toSnapshot(Telefone telefone);

    @Mapping(target = "ddd", qualifiedByName = "mapIfValidString")
    @Mapping(target = "numero", qualifiedByName = "mapIfValidString")
    void updateTelefone(TelefoneDTO dto, @MappingTarget Telefone telefone);
}
