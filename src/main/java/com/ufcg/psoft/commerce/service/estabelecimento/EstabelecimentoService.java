package com.ufcg.psoft.commerce.service.estabelecimento;

import com.ufcg.psoft.commerce.model.Estabelecimento;

public interface EstabelecimentoService {
    public Estabelecimento criarEstabelecimento(Estabelecimento estabelecimento);
    public Estabelecimento editarEstabelecimento(Long id, String codigoAcesso, Estabelecimento estabelecimentoEditado);
    public void removerEstabelecimento(Long id, String codigoAcesso);
}
