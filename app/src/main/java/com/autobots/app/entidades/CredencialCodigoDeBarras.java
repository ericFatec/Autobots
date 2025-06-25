package com.autobots.app.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("CODIGO")
public class CredencialCodigoDeBarras extends Credencial{
    @Column(unique = true)
    private String codigo;

    @Override
    public String getLoginHandle() {
        return codigo;
    }
    @Override
    public String getPassword() {
        return "";
    }
}
