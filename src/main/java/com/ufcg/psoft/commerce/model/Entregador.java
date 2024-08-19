package com.ufcg.psoft.commerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Entregador {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nome;
    private String placaVeiculo;
    private String tipoVeiculo; // Moto ou Carro
    private String corVeiculo;
    private String codigoAcesso;

    private boolean aprovado; // Estado de aprovação pelo estabelecimento
}

