package com.autobots.app.types.dtos;

import lombok.Data;

@Data
public class EnderecoDTO {
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String numero;
    private String codigoPostal;
    private String informacoesAdicionais;
}
