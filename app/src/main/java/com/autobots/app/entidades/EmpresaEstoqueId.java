package com.autobots.app.entidades;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class EmpresaEstoqueId implements Serializable{
    private Long empresaId;
    private Long mercadoriaId;
}
