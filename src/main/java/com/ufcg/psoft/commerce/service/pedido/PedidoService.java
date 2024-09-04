package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoGetRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;

public interface PedidoService {
    PedidoGetRequestDTO criarPedido(PedidoPostPutRequestDTO dto, String codAcesso);

    PedidoGetRequestDTO recuperarPedido(Long id, String codAcesso);

    PedidoGetRequestDTO alterarPedido(Long id, PedidoPostPutRequestDTO dto, String codAcesso);

    void removerPedido(Long id, String codAcesso);
}
