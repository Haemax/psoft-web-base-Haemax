package com.ufcg.psoft.commerce.service.estabelecimento;

import com.ufcg.psoft.commerce.dto.EstabelecimentoGetDTO;
import com.ufcg.psoft.commerce.dto.EstabelecimentoPostPutDTO;
import com.ufcg.psoft.commerce.exception.CodigoDeAcessoInvalidoException;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstabelecimentoServiceImpl {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Transactional
    public EstabelecimentoGetDTO criarEstabelecimento(EstabelecimentoPostPutDTO estabelecimentoPostPutDTO) {
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setNome(estabelecimentoPostPutDTO.getNome());
        estabelecimento.setCodigoAcesso(estabelecimentoPostPutDTO.getCodigoAcesso());
        estabelecimento = estabelecimentoRepository.save(estabelecimento);

        return mapToGetDTO(estabelecimento);
    }

    @Transactional
    public EstabelecimentoGetDTO editarEstabelecimento(Long id, EstabelecimentoPostPutDTO estabelecimentoUpdateDTO) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow();
        if (!estabelecimento.getCodigoAcesso().equals(estabelecimentoUpdateDTO.getCodigoAcesso())) {
            throw new CodigoDeAcessoInvalidoException();
        }
        estabelecimento.setNome(estabelecimentoUpdateDTO.getNome());
        estabelecimento = estabelecimentoRepository.save(estabelecimento);

        return mapToGetDTO(estabelecimento);
    }

    @Transactional
    public void removerEstabelecimento(Long id, String codigoAcesso) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow();
        if (!estabelecimento.getCodigoAcesso().equals(codigoAcesso)) {
            throw new CodigoDeAcessoInvalidoException();
        }
        estabelecimentoRepository.delete(estabelecimento);
    }

    private EstabelecimentoGetDTO mapToGetDTO(Estabelecimento estabelecimento) {
        EstabelecimentoGetDTO dto = new EstabelecimentoGetDTO();
        dto.setNome(estabelecimento.getNome());
        dto.setId(estabelecimento.getId());
        return dto;
    }
}


