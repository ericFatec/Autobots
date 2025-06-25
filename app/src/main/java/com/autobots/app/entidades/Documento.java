package com.autobots.app.entidades;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.app.types.enums.TipoDocumento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"tipo", "numero"}))
public class Documento extends RepresentationModel<Documento> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Tipo do documento é obrigatório!")
    private TipoDocumento tipo;

    @Column(nullable = false)
    @NotBlank(message = "Número do documento é obrigatório!")
    private String numero;
}
