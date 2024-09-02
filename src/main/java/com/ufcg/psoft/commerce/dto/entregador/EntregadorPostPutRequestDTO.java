package com.ufcg.psoft.commerce.dto.entregador;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntregadorPostPutRequestDTO {

    @NotBlank(message = "É obrigatório informar o nome do entregador")
    @JsonProperty("nome")
    private String nome;

    @NotBlank(message = "É obrigatório informar a placa do veículo")
    @Pattern(regexp = "^[A-Z]{3}\\d{1}[A-Z]{1}\\d{2}$", message = "Placa do veículo deve seguir o padrão mercosul")
    @JsonProperty("placaVeiculo")
    private String placaVeiculo;

    @NotBlank(message = "É obrigatório informar o tipo do veículo")
    @JsonProperty("tipoVeiculo")
    private String tipoVeiculo;

    @NotBlank(message = "É obrigatório informar a cor do veículo")
    @JsonProperty("corVeiculo")
    private String corVeiculo;

    @NotBlank(message = "É obrigatório informar o código de acesso")
    @Pattern(regexp = "^\\d{6}$", message = "Codigo de acesso deve ter exatamente 6 digitos numericos")
    @JsonProperty("codigoAcesso")
    private String codigoAcesso;
}