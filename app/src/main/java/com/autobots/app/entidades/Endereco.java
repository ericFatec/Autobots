package com.autobots.app.entidades;

import org.hibernate.annotations.Formula;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Estado é obrigatório!")
    private String estado;

    @Column(nullable = false)
    @NotBlank(message = "Cidade é obrigatório!")
    private String cidade;

    @Column(nullable = false)
    @NotBlank(message = "Bairro é obrigatório!")
    private String bairro;

    @Column(nullable = false)
    @NotBlank(message = "Rua é obrigatório!")
    private String rua;

    @Column(nullable = false)
    @NotBlank(message = "Número é obrigatório!")
    private String numero;

    @Column(nullable = false)
    @NotBlank(message = "Código postal é obrigatório!")
    private String codigoPostal;

    @Column
    private String informacoesAdicionais;

    @Formula("(select u.id from usuario u where u.endereco_id = id)")
    private Long usuarioId;

    @Formula("(select em.id from empresa em where em.endereco_id = id)")
    private Long empresaId;
}
