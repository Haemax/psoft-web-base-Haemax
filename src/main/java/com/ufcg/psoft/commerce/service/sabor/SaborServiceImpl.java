package com.ufcg.psoft.commerce.service.sabor;

import com.ufcg.psoft.commerce.dto.cliente.ClienteGetRequestDTO;
import com.ufcg.psoft.commerce.dto.sabor.SaborGetRequestDTO;
import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.model.TIPO_SABOR;
import com.ufcg.psoft.commerce.repository.SaborRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaborServiceImpl {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SaborRepository saborRepository;

    public SaborGetRequestDTO criarSabor(Estabelecimento estabelecimento, SaborPostPutRequestDTO dto) {
        Sabor sabor = modelMapper.map(dto, Sabor.class);
        estabelecimento.addSabor(sabor);
        return modelMapper.map(sabor, SaborGetRequestDTO.class);
    }


    public SaborGetRequestDTO recuperar(Long id) {
        Sabor sabor = saborRepository.findById(id)
                .orElseThrow(ClienteNaoExisteException::new);
        return modelMapper.map(sabor, SaborGetRequestDTO.class);
    }

    public List<SaborGetRequestDTO> listarSabores(Estabelecimento estabelecimento) {
        return estabelecimento.getSabores().stream()
                .filter(Sabor::isDisponivel)
                .map(SaborGetRequestDTO::new)
                .collect(Collectors.toList());
    }

    public List<SaborGetRequestDTO> listarSaboresPorTipo(Estabelecimento estabelecimento, TIPO_SABOR tipo) {
        return estabelecimento.getSabores().stream()
                .filter(sabor -> sabor.getTipo().equals(tipo))
                .map(SaborGetRequestDTO::new)
                .collect(Collectors.toList());
    }

    public SaborGetRequestDTO alterarSabor(Estabelecimento estabelecimento, Long idSabor, SaborPostPutRequestDTO dto) {
        Sabor sabor = estabelecimento.getSabor(idSabor); //lança SaborNaoExisteException se o sabor não existir no estabelecimento
        Sabor saborAtualizado = modelMapper.map(dto, Sabor.class);
        estabelecimento.updateSabor(sabor.getId(), saborAtualizado);
        return modelMapper.map(saborAtualizado, SaborGetRequestDTO.class);
    }

    public void removerSabor(Estabelecimento estabelecimento, Long idSabor) {
        estabelecimento.removeSabor(idSabor);
    }
}

