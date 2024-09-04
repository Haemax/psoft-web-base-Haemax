package com.ufcg.psoft.commerce.dto.pedido;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.MetodoPagamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoPostPutRequestDTO {

    @NotBlank(message = "É obrigatório informar o id do cliente")
    @JsonProperty("idCliente")
    private Long idCliente;

    @NotBlank(message = "É obrigatório informar os ids das pizzas do pedido")
    @JsonProperty("idPizzas")
    private List<Long> idPizzas;

    @JsonProperty("enderecoEntrega")
    private String enderecoEntrega;

    @NotBlank(message = "É obrigatório informar o método de pagamento")
    @JsonProperty("metodoPagamento")
    private MetodoPagamento metodoPagamento;

    @NotBlank(message = "É obrigatório informar o código de acesso do cliente")
    @Pattern(regexp = "^\\d{6}$", message = "Codigo de acesso deve ter exatamente 6 digitos numericos")
    @JsonProperty("codigoAcesso")
    private String codigoAcesso;

}
