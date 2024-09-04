package com.ufcg.psoft.commerce.service.entregador;

import java.util.List;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorGetRequestDTO;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorPostPutRequestDTO;

public interface EntregadorService {

    EntregadorGetRequestDTO criar(EntregadorPostPutRequestDTO entregadorPostPutRequestDTO);

    EntregadorGetRequestDTO recuperar(Long id);

    List<EntregadorGetRequestDTO> listar();

    EntregadorGetRequestDTO alterar(Long id, String codigoAcesso,
            EntregadorPostPutRequestDTO entregadorPostPutRequestDTO);

    void remover(Long id, String codigoAcesso);
}