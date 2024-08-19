package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.service.cliente.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/cliente",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @GetMapping("/{id}")
    public ResponseEntity<?> recuperarCliente(
            @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteService.recuperar(id));
    }

    @GetMapping("")
    public ResponseEntity<?> listarClientes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteService.listar());
    }

    @PostMapping()
    public ResponseEntity<?> criarCliente(
            @RequestBody @Valid ClientePostPutRequestDTO clientePostPutRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clienteService.criar(clientePostPutRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCliente(
            @PathVariable Long id,
            @RequestParam String codigoAcesso,
            @RequestBody @Valid ClientePostPutRequestDTO clientePostPutRequestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteService.alterar(id, codigoAcesso, clientePostPutRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirCliente(
            @PathVariable Long id,
            @RequestParam String codigoAcesso) {
        clienteService.remover(id, codigoAcesso);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }

}
