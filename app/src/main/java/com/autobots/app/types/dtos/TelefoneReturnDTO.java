package com.autobots.app.types.dtos;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class TelefoneReturnDTO extends RepresentationModel<TelefoneReturnDTO>{
    @EqualsAndHashCode.Include
    private Long id;
    
    private String ddd;
    private String numero;
    private Long usuarioId;
    private Long empresaId;
}
