package com.ufcg.psoft.commerce.dto.pedido;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.dto.cliente.ClienteGetRequestDTO;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.model.MetodoPagamento;
import com.ufcg.psoft.commerce.model.Pizza;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoGetRequestDTO {
    @JsonProperty("id")
    @Id
    private Long id;

    @JsonProperty("cliente")
    private ClienteGetRequestDTO cliente;

    @JsonProperty("pizzas")
    private List<Pizza> pizzas;

    @JsonProperty("enderecoEntrega")
    private String enderecoEntrega;

    @JsonProperty("valorTotal")
    private double valorTotal;

    @JsonProperty("metodoPagamento")
    private MetodoPagamento metodoPagamento;
}
