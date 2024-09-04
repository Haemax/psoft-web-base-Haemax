package com.ufcg.psoft.commerce.dto.sabor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.Sabor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaborGetRequestDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("tipo")
    private String tipo;

    @JsonProperty("valor_m")
    private String valor_m;

    @JsonProperty("valor_g")
    private String valor_g;

    @JsonProperty("disponivel")
    private String disponivel;

    public SaborGetRequestDTO(Sabor sabor) {
    }
}
