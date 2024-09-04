package com.ufcg.psoft.commerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Entregadores")
public class Entregador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String placaVeiculo;

    @Column(nullable = false)
    private String tipoVeiculo;

    @Column(nullable = false)
    private String corVeiculo;

    @Column(nullable = false, unique = true)
    private String codigoAcesso;

    @ManyToOne
    private Estabelecimento estabelecimento;
}
