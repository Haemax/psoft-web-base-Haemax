package com.ufcg.psoft.commerce.exception;

public class EstabelecimentoNaoExisteException extends CommerceException {
    public EstabelecimentoNaoExisteException() {super("O estabelecimento informado não existe!");}

    public EstabelecimentoNaoExisteException(String message) {super(message);}
}
