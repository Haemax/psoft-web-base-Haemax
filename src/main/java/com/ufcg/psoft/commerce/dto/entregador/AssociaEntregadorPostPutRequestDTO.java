package com.ufcg.psoft.commerce.dto.entregador;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssociaEntregadorPostPutRequestDTO {

    @NotNull(message = "É obrigatório informar o ID do entregador")
    @JsonProperty("idEntregador")
    private Long id;
}
