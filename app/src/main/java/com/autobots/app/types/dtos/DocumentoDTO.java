package com.autobots.app.types.dtos;

import com.autobots.app.types.enums.TipoDocumento;

import lombok.Data;

@Data
public class DocumentoDTO {
    private TipoDocumento tipo;
    private String numero;
}
