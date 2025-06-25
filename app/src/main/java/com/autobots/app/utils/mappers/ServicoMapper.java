package com.autobots.app.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.autobots.app.entidades.Servico;
import com.autobots.app.entidades.snapshots.ServicoSnapshot;
import com.autobots.app.types.dtos.ServicoDTO;
import com.autobots.app.types.dtos.ServicoReturnDTO;
import com.autobots.app.utils.validators.StringVerificador;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = StringVerificador.class
)
public interface ServicoMapper {
    @Mapping(target = "nome", qualifiedByName = "mapIfValidString")
    @Mapping(target = "descricao", qualifiedByName = "mapIfValidString")
    Servico toObject(ServicoDTO servicoDTO);
    ServicoDTO toDTO(Servico servico);
    ServicoReturnDTO toReturnDTO(Servico servico);

    @Mapping(target = "servicoId", source = "id")
    ServicoSnapshot toSnapshot(Servico servico);

    @Mapping(target = "nome", qualifiedByName = "mapIfValidString")
    @Mapping(target = "descricao", qualifiedByName = "mapIfValidString")
    void updateServico(ServicoDTO dto, @MappingTarget Servico servico);
}
