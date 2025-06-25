package com.autobots.app.entidades;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
public class Endereco extends RepresentationModel<Endereco>{
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
}
