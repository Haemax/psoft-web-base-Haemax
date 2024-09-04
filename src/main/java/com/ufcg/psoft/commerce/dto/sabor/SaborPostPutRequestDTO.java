package com.ufcg.psoft.commerce.dto.sabor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.TIPO_SABOR;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaborPostPutRequestDTO {

    @JsonProperty("nome")
    @NotBlank(message = "É obrigatório informar o nome do sabor")
    private String nome;

    @JsonProperty("tipo")
    @NotBlank(message = "É obrigatório informar o tipo de sabor")
    private TIPO_SABOR tipo;

    @JsonProperty("valor_m")
    @NotBlank(message = "É obrigatório informar o valor da pizza média")
    private double valorMedia;

    @JsonProperty("valor_g")
    @NotBlank(message = "É obrigatório informar o valor da pizza grande")
    private double valorGrande;

    @JsonProperty("disponivel")
    private boolean disponivel;
}
