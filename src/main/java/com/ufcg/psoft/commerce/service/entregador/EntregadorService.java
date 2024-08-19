package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.model.Entregador;

public interface EntregadorService {
    public Entregador criarEntregador(Entregador entregador);
    public Entregador editarEntregador(Long id, String codigoAcesso, Entregador entregadorEditado);
    public void removerEntregador(Long id, String codigoAcessoEstabelecimento);
    public Entregador aprovarEntregador(Long id, String codigoAcessoEstabelecimento);
}
