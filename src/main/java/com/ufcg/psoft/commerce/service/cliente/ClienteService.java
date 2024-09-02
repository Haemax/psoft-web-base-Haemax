package com.ufcg.psoft.commerce.service.cliente;

import com.ufcg.psoft.commerce.dto.cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.cliente.ClienteGetRequestDTO;
import com.ufcg.psoft.commerce.model.TIPO_SABOR;


import java.util.List;

public interface ClienteService {

    ClienteGetRequestDTO alterar(Long id, String codigoAcesso, ClientePostPutRequestDTO clientePostPutRequestDTO);

    List<ClienteGetRequestDTO> listar();

    ClienteGetRequestDTO recuperar(Long id);

    ClienteGetRequestDTO criar(ClientePostPutRequestDTO clientePostPutRequestDTO);

    void remover(Long id, String codigoAcesso);

    String exibirCardapio(Long idEstabelecimento);

    String exibirCardapioPorTipo(Long idEstabelecimento, TIPO_SABOR tipo);
}
