package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorGetRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.service.entregador.EntregadorService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping(value = "/entregadores",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class EntregadorController {

    @Autowired
    private EntregadorService entregadorService;

    @GetMapping("/{id}")
    public ResponseEntity<EntregadorGetRequestDTO> recuperarEntregador(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(entregadorService.recuperar(id));
    }

    @GetMapping("")
    public ResponseEntity<List<EntregadorGetRequestDTO>> listarEntregadores() {
        return ResponseEntity.status(HttpStatus.OK).body(entregadorService.listar());
    }

    @PostMapping()
    public ResponseEntity<?> criarEntregador(
            @RequestBody @Valid EntregadorPostPutRequestDTO entregadorPostPutRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(entregadorService.criar(entregadorPostPutRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarEntregador(
            @PathVariable Long id,
            @RequestParam String codigoAcesso,
            @RequestBody @Valid EntregadorPostPutRequestDTO entregadorPostPutRequestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(entregadorService.alterar(id, codigoAcesso, entregadorPostPutRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirEntregador(@PathVariable Long id, @RequestParam String codigoAcesso) {
        entregadorService.remover(id, codigoAcesso);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
