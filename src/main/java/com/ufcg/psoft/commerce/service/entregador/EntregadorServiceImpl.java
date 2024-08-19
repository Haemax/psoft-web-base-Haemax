package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.dto.EntregadorGetDTO;
import com.ufcg.psoft.commerce.dto.EntregadorPostPutDTO;
import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.exception.CodigoDeAcessoInvalidoException;
import com.ufcg.psoft.commerce.model.Entregador;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.service.estabelecimento.EstabelecimentoService;
import com.ufcg.psoft.commerce.service.estabelecimento.EstabelecimentoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntregadorServiceImpl {

    @Autowired
    private EntregadorRepository entregadorRepository;
    private EstabelecimentoRepository estabelecimentoRepository;
    private EstabelecimentoServiceImpl estabelecimentoService;

    @Transactional
    public EntregadorGetDTO criarEntregador(EntregadorPostPutDTO entregadorCreateDTO) {
        Entregador entregador = new Entregador();
        entregador.setNome(entregadorCreateDTO.getNomeCompleto());
        entregador.setPlacaVeiculo(entregadorCreateDTO.getPlacaVeiculo());
        entregador.setTipoVeiculo(entregadorCreateDTO.getTipoVeiculo());
        entregador.setCorVeiculo(entregadorCreateDTO.getCorVeiculo());
        entregador.setCodigoAcesso(entregadorCreateDTO.getCodigoAcesso());
        entregador.setAprovado(false);
        entregador = entregadorRepository.save(entregador);

        return mapToGetDTO(entregador);
    }

    @Transactional
    public EntregadorGetDTO editarEntregador(Long id, EntregadorPostPutDTO entregadorUpdateDTO) {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(ClienteNaoExisteException::new);
        if (!entregador.getCodigoAcesso().equals(entregadorUpdateDTO.getCodigoAcesso())) {
            throw new CodigoDeAcessoInvalidoException();
        }
        entregador.setNome(entregadorUpdateDTO.getNomeCompleto());
        entregador.setPlacaVeiculo(entregadorUpdateDTO.getPlacaVeiculo());
        entregador.setTipoVeiculo(entregadorUpdateDTO.getTipoVeiculo());
        entregador.setCorVeiculo(entregadorUpdateDTO.getCorVeiculo());
        entregador = entregadorRepository.save(entregador);

        return mapToGetDTO(entregador);
    }

    @Transactional
    public void removerEntregador(Long id, String codigoAcessoEstabelecimento) {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(() -> new RuntimeException("Entregador não encontrado."));
        entregadorRepository.delete(entregador);
    }

    @Transactional
    public EntregadorGetDTO aprovarEntregador(Long id, String codigoAcessoEstabelecimento) {
        Entregador entregador = entregadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entregador não encontrado."));

        Estabelecimento estabelecimento = estabelecimentoRepository.findByCodigoAcesso(codigoAcessoEstabelecimento)
                .orElseThrow(() -> new RuntimeException("Código de acesso do estabelecimento incorreto."));

        entregador.setAprovado(true);
        entregador = entregadorRepository.save(entregador);

        return mapToGetDTO(entregador);
    }

    private EntregadorGetDTO mapToGetDTO(Entregador entregador) {
        EntregadorGetDTO dto = new EntregadorGetDTO();
        dto.setId(entregador.getId());
        dto.setNomeCompleto(entregador.getNome());
        dto.setPlacaVeiculo(entregador.getPlacaVeiculo());
        dto.setTipoVeiculo(entregador.getTipoVeiculo());
        dto.setCorVeiculo(entregador.getCorVeiculo());
        dto.setAprovado(entregador.isAprovado());
        return dto;
    }
}
