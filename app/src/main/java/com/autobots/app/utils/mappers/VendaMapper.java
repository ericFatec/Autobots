package com.autobots.app.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.autobots.app.entidades.Vendas;
import com.autobots.app.types.dtos.VendaReturnDTO;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = { UsuarioMapper.class, VendaMapperHelper.class }
)
public interface VendaMapper {
    @Mapping(target = "cliente", source = ".", qualifiedByName = "resolveCliente")
    @Mapping(target = "vendedor", source = ".", qualifiedByName = "resolveVendedor")
    VendaReturnDTO toReturnDTO(Vendas venda);
}
