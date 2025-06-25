package com.autobots.app.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.autobots.app.entidades.Documento;
import com.autobots.app.types.dtos.DocumentoDTO;
import com.autobots.app.types.dtos.DocumentoReturnDTO;
import com.autobots.app.utils.validators.StringVerificador;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = StringVerificador.class
)
public interface DocumentoMapper {
    @Mapping(target = "numero", qualifiedByName = "mapIfValidString")
    Documento toObject(DocumentoDTO documentoDTO);
    DocumentoDTO toDTO(Documento documento);
    DocumentoReturnDTO toReturnDTO(Documento documento);

    @Mapping(target = "numero", qualifiedByName = "mapIfValidString")
    void updateDocumento(DocumentoDTO dto, @MappingTarget Documento documento);
}
