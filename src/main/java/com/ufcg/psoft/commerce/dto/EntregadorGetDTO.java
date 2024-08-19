package com.ufcg.psoft.commerce.dto;

import lombok.Data;

@Data
public class EntregadorGetDTO {
    private Long id;
    private String nomeCompleto;
    private String placaVeiculo;
    private String tipoVeiculo;
    private String corVeiculo;
    private boolean aprovado;
}
