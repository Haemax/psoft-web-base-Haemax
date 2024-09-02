package com.ufcg.psoft.commerce.exception;

public class EstabelecimentoNaoExisteException extends CommerceException {
    public EstabelecimentoNaoExisteException() {super("Estabelecimento.java Não foi encontrado");}

    public EstabelecimentoNaoExisteException(String message) {super(message);}
}
