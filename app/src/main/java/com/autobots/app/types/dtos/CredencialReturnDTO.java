package com.autobots.app.types.dtos;

import java.time.Instant;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.app.types.enums.TipoCredencial;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class CredencialReturnDTO extends RepresentationModel<CredencialReturnDTO>{
    private Long id;
    private Instant criacao;
    private Instant ultimoAcesso;
    private boolean inativo;
    private TipoCredencial tipo;
    private String login;
    private String senha;
    private String codigo;
    private Long usuarioId;
}
