package com.ufcg.psoft.commerce.service.cliente;


import com.ufcg.psoft.commerce.dto.sabor.SaborGetRequestDTO;
import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.exception.CodigoDeAcessoInvalidoException;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.model.TIPO_SABOR;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import com.ufcg.psoft.commerce.dto.cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.cliente.ClienteGetRequestDTO;

import com.ufcg.psoft.commerce.service.estabelecimento.EstabelecimentoService;
import com.ufcg.psoft.commerce.service.util.CredenciaisService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EstabelecimentoService estabelecimentoService;

    @Autowired
    private CredenciaisService credenciaisService;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public ClienteGetRequestDTO alterar(Long id, String codigoAcesso, ClientePostPutRequestDTO clientePostPutRequestDTO) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(ClienteNaoExisteException::new);

        if(!credenciaisService.validaCodigoAcesso(codigoAcesso, cliente.getCodigoAcesso())){
            throw new CodigoDeAcessoInvalidoException();
        }

        modelMapper.map(clientePostPutRequestDTO, cliente);
        clienteRepository.save(cliente);
        return modelMapper.map(cliente, ClienteGetRequestDTO.class);
    }

    @Override
    public ClienteGetRequestDTO criar(ClientePostPutRequestDTO clientePostPutRequestDTO) {
        Cliente cliente = modelMapper.map(clientePostPutRequestDTO, Cliente.class);
        clienteRepository.save(cliente);
        return modelMapper.map(cliente, ClienteGetRequestDTO.class);
    }

    @Override
    public void remover(Long id, String codigoAcesso) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(ClienteNaoExisteException::new);

        if(!credenciaisService.validaCodigoAcesso(codigoAcesso, cliente.getCodigoAcesso())){
            throw new CodigoDeAcessoInvalidoException();
        }

        clienteRepository.delete(cliente);
    }

    @Override
    public List<ClienteGetRequestDTO> listar() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(ClienteGetRequestDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteGetRequestDTO recuperar(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(ClienteNaoExisteException::new);
        return new ClienteGetRequestDTO(cliente);
    }

    @Override
    public Cliente recuperarObj(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(ClienteNaoExisteException::new);
    }

    //exibição de cardapio
    @Override
    public String exibirCardapio(Long idEstabelecimento) {
        List<SaborGetRequestDTO> sabores = estabelecimentoService.listarSabores(idEstabelecimento);

        StringBuilder ret = new StringBuilder();

        for(SaborGetRequestDTO sabor : sabores){
            ret.append(sabor.getNome()).append(", valor média: ")
                    .append(sabor.getValor_m())
                    .append(" valor grande: ")
                    .append(sabor.getValor_g())
                    .append("\n");
        }

        return ret.toString();
    }

    @Override
    public String exibirCardapioPorTipo(Long idEstabelecimento, TIPO_SABOR tipo) {
        List<SaborGetRequestDTO> sabores = estabelecimentoService.listarSaboresPorTipo(idEstabelecimento, tipo);

        StringBuilder ret = new StringBuilder();

        for(SaborGetRequestDTO sabor : sabores){
            ret.append(sabor.getNome()).append(", valor média: ")
                    .append(sabor.getValor_m())
                    .append(" valor grande: ")
                    .append(sabor.getValor_g())
                    .append("\n");
        }

        return ret.toString();
    }
}

