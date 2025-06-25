package com.autobots.app.entidades;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"ddd", "numero"}))
public class Telefone extends RepresentationModel<Telefone>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "O DDD do telefone é obrigatório!")
    private String ddd;

    @Column(nullable = false)
    @NotBlank(message = "O número do telefone é obrigatório!")
    private String numero;
}
