package com.autobots.app.entidades;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class UsuarioEstoqueId implements Serializable{
    private Long usuarioId;
    private Long mercadoriaId;
}
