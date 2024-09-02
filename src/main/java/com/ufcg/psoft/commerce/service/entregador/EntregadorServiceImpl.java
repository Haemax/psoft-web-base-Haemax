package com.ufcg.psoft.commerce.service.entregador;

import java.util.List;
import java.util.stream.Collectors;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorGetRequestDTO;
import com.ufcg.psoft.commerce.service.util.CredenciaisService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.CodigoDeAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.EntregadorNaoExisteException;
import com.ufcg.psoft.commerce.model.Entregador;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;

@Service
public class EntregadorServiceImpl implements EntregadorService {

    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    private CredenciaisService credenciaisService;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public EntregadorGetRequestDTO criar(EntregadorPostPutRequestDTO entregadorPostPutRequestDTO) {
        Entregador entregador = modelMapper.map(entregadorPostPutRequestDTO, Entregador.class);
        entregadorRepository.save(entregador);
        return modelMapper.map(entregador, EntregadorGetRequestDTO.class);
    }

    @Override
    public EntregadorGetRequestDTO alterar(Long id, String codigoAcesso,
            EntregadorPostPutRequestDTO entregadorPostPutRequestDTO) {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(EntregadorNaoExisteException::new);

        if(!credenciaisService.validaCodigoAcesso(codigoAcesso, entregador.getCodigoAcesso())){
            throw new CodigoDeAcessoInvalidoException();
        }

        modelMapper.map(entregadorPostPutRequestDTO, entregador);
        entregadorRepository.save(entregador);
        return modelMapper.map(entregador, EntregadorGetRequestDTO.class);
    }

    @Override
    public List<EntregadorGetRequestDTO> listar() {
        List<Entregador> entregadores = entregadorRepository.findAll();
        return entregadores.stream()
                .map(EntregadorGetRequestDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public EntregadorGetRequestDTO recuperar(Long id) {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(EntregadorNaoExisteException::new);
        return modelMapper.map(entregador, EntregadorGetRequestDTO.class);
    }

    @Override
    public void remover(Long id, String codigoAcesso) {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(EntregadorNaoExisteException::new);

        if(!credenciaisService.validaCodigoAcesso(codigoAcesso, entregador.getCodigoAcesso())){
            throw new CodigoDeAcessoInvalidoException();
        }

        entregadorRepository.delete(entregador);

    }

}