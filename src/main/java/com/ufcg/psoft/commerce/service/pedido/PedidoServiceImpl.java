package com.ufcg.psoft.commerce.service.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoGetRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.PedidoNaoExisteException;
import com.ufcg.psoft.commerce.exception.SaborNaoExisteException;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.model.Pizza;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import com.ufcg.psoft.commerce.service.cliente.ClienteService;
import com.ufcg.psoft.commerce.service.util.CredenciaisService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService{

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CredenciaisService credenciaisService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PedidoGetRequestDTO criarPedido(PedidoPostPutRequestDTO dto, String codAcesso) {
        Cliente cliente = clienteService.recuperarObj(dto.getIdCliente());
        Pedido pedido = modelMapper.map(dto, Pedido.class);

        credenciaisService.validaCodigoAcesso(cliente.getCodigoAcesso(), codAcesso);

        for (Pizza pizza : pedido.getPizzas()) {
            List<Sabor> sabores = pizza.getSabores();
            if(!sabores.containsAll(sabores))
                throw new SaborNaoExisteException();
        }

        pedido.calcularValorTotal();

        //desconto
        pedido.setValorTotal(pedido.getMetodoPagamento().aplicarDesconto(pedido.getValorTotal()));

        //verifica o endereço de entrega
        if (pedido.getEnderecoEntrega().isBlank()) {
            pedido.setEnderecoEntrega(cliente.getEndereco());
        }

        return modelMapper.map(pedidoRepository.save(pedido), PedidoGetRequestDTO.class);
    }

    @Override
    public PedidoGetRequestDTO recuperarPedido(Long id, String codAcesso) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(PedidoNaoExisteException::new);

        credenciaisService.validaCodigoAcesso(pedido.getCliente().getCodigoAcesso(), codAcesso);

        return modelMapper.map(pedido, PedidoGetRequestDTO.class);
    }

    @Override
    public PedidoGetRequestDTO alterarPedido(Long id, PedidoPostPutRequestDTO dto, String codAcesso) {
        Pedido pedido = modelMapper.map(recuperarPedido(id, codAcesso), Pedido.class);
        Pedido pedidoAtualizado = modelMapper.map(dto, Pedido.class);

        pedido.setPizzas(pedidoAtualizado.getPizzas());
        pedido.setEnderecoEntrega(pedidoAtualizado.getEnderecoEntrega());
        pedido.setMetodoPagamento(pedidoAtualizado.getMetodoPagamento());

        //recalcula o valor e aplica o desconto
        pedido.calcularValorTotal();
        pedido.setValorTotal(pedido.getMetodoPagamento().aplicarDesconto(pedido.getValorTotal()));

        return modelMapper.map(pedidoRepository.save(pedido), PedidoGetRequestDTO.class);
    }

    @Override
    public void removerPedido(Long id, String codAcesso) {
        Pedido pedido = modelMapper.map(recuperarPedido(id, codAcesso), Pedido.class);

        credenciaisService.validaCodigoAcesso(pedido.getCliente().getCodigoAcesso(), codAcesso);

        pedidoRepository.delete(pedido);
    }
}

