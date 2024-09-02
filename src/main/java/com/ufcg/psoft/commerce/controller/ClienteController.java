package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.cliente.ClientePostPutRequestDTO;

import com.ufcg.psoft.commerce.dto.cliente.ClienteGetRequestDTO;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.model.TIPO_SABOR;
import com.ufcg.psoft.commerce.service.cliente.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/clientes",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping()
    public ResponseEntity<ClienteGetRequestDTO> criarCliente(
            @RequestBody @Valid ClientePostPutRequestDTO dto) {
        ClienteGetRequestDTO cliente = clienteService.criar(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteGetRequestDTO> recuperarCliente(
            @PathVariable Long id) {
        ClienteGetRequestDTO cliente = clienteService.recuperar(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteGetRequestDTO> atualizarCliente(
            @PathVariable Long id,
            @RequestParam String codigoAcesso,
            @RequestBody @Valid ClientePostPutRequestDTO clientePostPutRequestDto) {
        ClienteGetRequestDTO cliente = clienteService.alterar(id, codigoAcesso, clientePostPutRequestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(
            @PathVariable Long id,
            @RequestParam String codigoAcesso) {
        clienteService.remover(id, codigoAcesso);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/admin/clientList")
    public ResponseEntity<List<ClienteGetRequestDTO>> listarClientes() {
        List<ClienteGetRequestDTO> clientes = clienteService.listar();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientes);
    }

    @GetMapping("/estabelecimentos/{idEstabelecimento}/cardapio/")
    public ResponseEntity<String> exibirCardapio(@RequestParam Long idEstabelecimento) {
        String ret = clienteService.exibirCardapio(idEstabelecimento);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ret);
    }

    @GetMapping("/estabelecimentos/{idEstabelecimento}/cardapioTipo")
    public ResponseEntity<String> exibirCardapioPorTipo(@RequestParam Long idEstabelecimento,
                                                             @RequestParam TIPO_SABOR tipo) {
        String ret = clienteService.exibirCardapioPorTipo(idEstabelecimento, tipo);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ret);
    }
}
