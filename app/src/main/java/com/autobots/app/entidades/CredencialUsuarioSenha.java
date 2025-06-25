package com.autobots.app.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("SENHA")
public class CredencialUsuarioSenha extends Credencial{
    @Column(unique = true)
    private String login;
    @Column
    private String senha;
}
