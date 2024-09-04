package com.ufcg.psoft.commerce.dto.estabelecimento;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Sabor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentoGetRequestDTO {
    @JsonProperty("nome")
    private String nome;

    @JsonProperty("sabores")
    private List<Sabor> sabores;

    @JsonProperty("entregadoresAutorizados")
    private List<Sabor> entregadoresAutorizados;

    public EstabelecimentoGetRequestDTO(Estabelecimento estabelecimento) {
    }
}