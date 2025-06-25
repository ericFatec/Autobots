package com.autobots.app.types.dtos;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.app.types.enums.TipoDocumento;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class DocumentoReturnDTO extends RepresentationModel<DocumentoReturnDTO>{
    @EqualsAndHashCode.Include
    private Long id;

    private TipoDocumento tipo;
    private String numero;
    private Long usuarioId;
}