package com.ufcg.psoft.commerce.service.estabelecimento;

import com.ufcg.psoft.commerce.dto.entregador.AssociaEntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorGetRequestDTO;
import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoGetRequestDTO;
import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.sabor.SaborGetRequestDTO;
import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.TIPO_SABOR;

import java.util.List;

public interface EstabelecimentoService {

    EstabelecimentoGetRequestDTO criar(EstabelecimentoPostPutRequestDTO estabelecimentoDTO);

    EstabelecimentoGetRequestDTO alterar(Long id, String codigoAcesso,
                                         EstabelecimentoPostPutRequestDTO dto);

    List<EstabelecimentoGetRequestDTO> listar();

    EstabelecimentoGetRequestDTO recuperar(Long id);

    void remover(Long id, String codigoAcesso);

    //crud sabor
    SaborGetRequestDTO criarSabor(Long id, String codigoAcesso, SaborPostPutRequestDTO dto);

    List<SaborGetRequestDTO> listarSabores(Long id);

    List<SaborGetRequestDTO> listarSaboresPorTipo(Long id, TIPO_SABOR tipo);

    SaborGetRequestDTO alterarSabor(Long id, String codigoAcesso, Long idSabor, SaborPostPutRequestDTO dto);

    void removerSabor(Long id, String codigoAcesso, Long idSabor);

    //associação de entregador ao estabelecimento
    EntregadorGetRequestDTO associarEntregador(Long id, String codigoAcesso, AssociaEntregadorPostPutRequestDTO dto);
}
