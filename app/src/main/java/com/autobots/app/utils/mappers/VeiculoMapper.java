package com.autobots.app.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.autobots.app.entidades.Veiculo;
import com.autobots.app.entidades.snapshots.VeiculoSnapshot;
import com.autobots.app.types.dtos.VeiculoDTO;
import com.autobots.app.types.dtos.VeiculoReturnDTO;
import com.autobots.app.utils.validators.StringVerificador;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = StringVerificador.class
)
public interface VeiculoMapper {
    @Mapping(target = "modelo", qualifiedByName = "mapIfValidString")
    @Mapping(target = "placa", qualifiedByName = "mapIfValidString")
    Veiculo toObject(VeiculoDTO veiculoDTO);
    VeiculoDTO toDTO(Veiculo veiculo);
    VeiculoReturnDTO toReturnDTO(Veiculo veiculo);

    @Mapping(target = "veiculoId", source = "id")
    VeiculoSnapshot toSnapshot(Veiculo veiculo);

    @Mapping(target = "modelo", qualifiedByName = "mapIfValidString")
    @Mapping(target = "placa", qualifiedByName = "mapIfValidString")
    void updateVeiculo(VeiculoDTO dto, @MappingTarget Veiculo veiculo);
}
