package com.autobots.app.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.autobots.app.entidades.Mercadoria;
import com.autobots.app.entidades.snapshots.MercadoriaSnapshot;
import com.autobots.app.types.dtos.MercadoriaDTO;
import com.autobots.app.types.dtos.MercadoriaReturnDTO;
import com.autobots.app.utils.validators.StringVerificador;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = StringVerificador.class
)
public interface MercadoriaMapper {
    @Mapping(target = "nome", qualifiedByName = "mapIfValidString")
    @Mapping(target = "descricao", qualifiedByName = "mapIfValidString")
    Mercadoria toObject(MercadoriaDTO mercadoriaDTO);
    MercadoriaDTO toDTO(Mercadoria mercadoria);
    MercadoriaReturnDTO toReturnDTO(Mercadoria mercadoria);

    @Mapping(target = "mercadoriaId", source = "id")
    MercadoriaSnapshot toSnapshot(Mercadoria mercadoria);

    @Mapping(target = "nome", qualifiedByName = "mapIfValidString")
    @Mapping(target = "descricao", qualifiedByName = "mapIfValidString")
    void updateMercadoria(MercadoriaDTO dto, @MappingTarget Mercadoria mercadoria);
}
