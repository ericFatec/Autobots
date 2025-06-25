package com.autobots.app.types.dtos;

import com.autobots.app.entidades.Usuario;
import com.autobots.app.types.enums.TipoPerfil;

import lombok.Data;

@Data
public class RequesterDTO {
    TipoPerfil role;
    Usuario usuario;
}
