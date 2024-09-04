package com.ufcg.psoft.commerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Sabores")
public class Sabor{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "tipo")
    @Builder.Default
    private TIPO_SABOR tipo = TIPO_SABOR.SALGADO;

    @Column(name = "valor_m")
    @Builder.Default
    private double valorMedia = 0;

    @Column(name = "valor_g")
    @Builder.Default
    private double valorGrande = 0;

    @Column(name = "disponivel")
    @Builder.Default
    private boolean disponivel = true;
}

