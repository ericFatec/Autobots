package com.autobots.app.entidades.snapshots;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class EnderecoSnapshot {
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String numero;
    private String codigoPostal;
    private String informacoesAdicionais;
}
