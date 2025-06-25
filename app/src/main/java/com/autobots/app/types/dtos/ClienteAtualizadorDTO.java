package com.autobots.app.types.dtos;

import java.time.Instant;

import lombok.Data;

@Data
public class ClienteAtualizadorDTO {
    private String nome;
    private String nomeSocial;
    private Instant dataNascimento;
}
