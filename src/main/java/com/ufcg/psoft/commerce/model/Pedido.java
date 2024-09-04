package com.ufcg.psoft.commerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente", nullable = false)
    private Cliente cliente;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pedido")
    private List<Pizza> pizzas;

    @Column(name = "endereco_entrega")
    private String enderecoEntrega;

    @Column(name = "valor_total", nullable = false)
    private double valorTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pagamento", nullable = false)
    private MetodoPagamento metodoPagamento;

    @Column(name = "codigo_acesso", nullable = false)
    private String codigoAcesso;

    public void calcularValorTotal() {
        this.valorTotal = this.pizzas.stream()
                .mapToDouble(Pizza::calcularValor)
                .sum();
    }
}

