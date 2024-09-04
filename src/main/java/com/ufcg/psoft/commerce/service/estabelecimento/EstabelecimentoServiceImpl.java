package com.ufcg.psoft.commerce.service.estabelecimento;

import com.ufcg.psoft.commerce.dto.entregador.AssociaEntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorGetRequestDTO;
import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoGetRequestDTO;
import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.sabor.SaborGetRequestDTO;
import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.CodigoDeAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.EntregadorNaoExisteException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.commerce.model.Entregador;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.TIPO_SABOR;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.service.entregador.EntregadorService;
import com.ufcg.psoft.commerce.service.sabor.SaborServiceImpl;
import com.ufcg.psoft.commerce.service.util.CredenciaisService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class EstabelecimentoServiceImpl implements EstabelecimentoService {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private CredenciaisService credenciaisService;

    @Autowired
    private EntregadorService entregadorService;

    @Autowired
    private SaborServiceImpl saborServiceImpl;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EntregadorRepository entregadorRepository;

    public EstabelecimentoGetRequestDTO criar(EstabelecimentoPostPutRequestDTO dto) {
        Estabelecimento estabelecimento = modelMapper.map(dto, Estabelecimento.class);
        estabelecimentoRepository.save(estabelecimento);
        return modelMapper.map(estabelecimento, EstabelecimentoGetRequestDTO.class);
    }

    @Override
    public EstabelecimentoGetRequestDTO alterar(Long id, String codigoAcesso, EstabelecimentoPostPutRequestDTO dto) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(EstabelecimentoNaoExisteException::new);

        if (!credenciaisService.validaCodigoAcesso(codigoAcesso, estabelecimento.getCodigoAcesso())) {
            throw new CodigoDeAcessoInvalidoException();
        }

        modelMapper.map(dto, estabelecimento);
        estabelecimentoRepository.save(estabelecimento);
        return modelMapper.map(estabelecimento, EstabelecimentoGetRequestDTO.class);
    }

    @Override
    public List<EstabelecimentoGetRequestDTO> listar() {
        List<Estabelecimento> estabelecimentos = estabelecimentoRepository.findAll();
        return estabelecimentos.stream()
                .map(EstabelecimentoGetRequestDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public EstabelecimentoGetRequestDTO recuperar(Long id) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(EstabelecimentoNaoExisteException::new);
        return modelMapper.map(estabelecimento, EstabelecimentoGetRequestDTO.class);
    }

    @Override
    public void remover(Long id, String codigoAcesso) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(EstabelecimentoNaoExisteException::new);

        if (!credenciaisService.validaCodigoAcesso(codigoAcesso, estabelecimento.getCodigoAcesso())) {
            throw new CodigoDeAcessoInvalidoException();
        }
        estabelecimentoRepository.delete(estabelecimento);
    }

    // CRUD Sabores

    @Override
    public SaborGetRequestDTO criarSabor(Long id, String codigoAcesso, SaborPostPutRequestDTO dto) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(EstabelecimentoNaoExisteException::new);

        credenciaisService.validaCodigoAcesso(estabelecimento.getCodigoAcesso(), codigoAcesso);

        return saborServiceImpl.criarSabor(estabelecimento, dto);
    }

    @Override
    public List<SaborGetRequestDTO> listarSabores(Long id) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(EstabelecimentoNaoExisteException::new);

        return saborServiceImpl.listarSabores(estabelecimento);
    }

    @Override
    public List<SaborGetRequestDTO> listarSaboresPorTipo(Long id, TIPO_SABOR tipo) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(EstabelecimentoNaoExisteException::new);

        return saborServiceImpl.listarSaboresPorTipo(estabelecimento, tipo);
    }

    @Override
    public SaborGetRequestDTO alterarSabor(Long id, String codigoAcesso, Long idSabor, SaborPostPutRequestDTO dto) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(EstabelecimentoNaoExisteException::new);

        credenciaisService.validaCodigoAcesso(estabelecimento.getCodigoAcesso(), codigoAcesso);

        return saborServiceImpl.alterarSabor(estabelecimento, idSabor, dto);
    }

    @Override
    public void removerSabor(Long id, String codigoAcesso, Long idSabor) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(EstabelecimentoNaoExisteException::new);

        credenciaisService.validaCodigoAcesso(estabelecimento.getCodigoAcesso(), codigoAcesso);

        saborServiceImpl.removerSabor(estabelecimento, idSabor);
        estabelecimentoRepository.save(estabelecimento);
    }

    @Override
    public EntregadorGetRequestDTO associarEntregador(Long id, String codigoAcesso, AssociaEntregadorPostPutRequestDTO dto) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(EstabelecimentoNaoExisteException::new);

        Entregador entregador = entregadorRepository.findById(dto.getId())
                .orElseThrow(EntregadorNaoExisteException::new);

        estabelecimento.addEntregador(entregador);
        estabelecimentoRepository.save(estabelecimento);

        return modelMapper.map(entregador, EntregadorGetRequestDTO.class);
    }
}
