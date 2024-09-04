package com.ufcg.psoft.commerce.model;

public enum MetodoPagamento {
    CARTAO_CREDITO(0),
    CARTAO_DEBITO(0.025),
    PIX(0.05);

    private final double desconto;

    MetodoPagamento(double desconto) {
        this.desconto = desconto;
    }

    public double aplicarDesconto(double valor) {
        return valor * (1 - desconto);
    }
}

