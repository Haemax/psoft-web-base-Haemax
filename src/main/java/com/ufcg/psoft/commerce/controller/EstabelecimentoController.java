package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorGetRequestDTO;
import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoGetRequestDTO;
import com.ufcg.psoft.commerce.dto.estabelecimento.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.sabor.SaborGetRequestDTO;
import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.entregador.AssociaEntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.TIPO_SABOR;
import com.ufcg.psoft.commerce.service.estabelecimento.EstabelecimentoService;
import com.ufcg.psoft.commerce.service.util.CredenciaisService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/estabelecimentos",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class EstabelecimentoController {

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @PostMapping("/")
    public ResponseEntity<EstabelecimentoGetRequestDTO> criarEstabelecimento(
            @RequestBody @Valid EstabelecimentoPostPutRequestDTO dto) {
        EstabelecimentoGetRequestDTO estabelecimento = estabelecimentoService.criar(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(estabelecimento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstabelecimentoGetRequestDTO> recuperarEstabelecimento(
            @PathVariable Long id) {
        EstabelecimentoGetRequestDTO estabelecimento = estabelecimentoService.recuperar(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimento);
    }

    @GetMapping("/admin/storeList")
    public ResponseEntity<List<EstabelecimentoGetRequestDTO>> listarEstabelecimentos() {
        List<EstabelecimentoGetRequestDTO> estabelecimento = estabelecimentoService.listar();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimento);
    }


    @PutMapping("/{id}")
    public ResponseEntity<EstabelecimentoGetRequestDTO> atualizarEstabelecimento(
            @PathVariable Long id,
            @RequestParam String codigoAcesso,
            @RequestBody @Valid EstabelecimentoPostPutRequestDTO dto) {
        EstabelecimentoGetRequestDTO estabelecimento = estabelecimentoService.alterar(id, codigoAcesso, dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEstabelecimento(
            @PathVariable Long id,
            @RequestParam String codigoAcesso) {
        estabelecimentoService.remover(id, codigoAcesso);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


    @PostMapping("/{id}/sabores")
    public ResponseEntity<SaborGetRequestDTO> criarSabor(
            @PathVariable Long id,
            @RequestParam(value = "codigoAcesso") String codigoAcesso,
            @RequestBody @Valid SaborPostPutRequestDTO dto) {

        SaborGetRequestDTO sabor = estabelecimentoService.criarSabor(id, codigoAcesso, dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sabor);
    }

    @PutMapping("{id}/sabores/{idSabor}")
    public ResponseEntity<SaborGetRequestDTO> updateSabor(
            @PathVariable Long id,
            @PathVariable Long idSabor,
            @RequestParam(value = "codigoAcesso") String codigoAcesso,
            @RequestBody @Valid SaborPostPutRequestDTO dto) {

        SaborGetRequestDTO sabor = estabelecimentoService.alterarSabor(id, codigoAcesso, idSabor, dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sabor);
    }

    @DeleteMapping("{id}/sabores/{idSabor}")
    public ResponseEntity<Void> deletarSabor(
            @PathVariable Long id,
            @PathVariable Long idSabor,
            @RequestParam(value = "codigoAcesso") String codigoAcesso) {

      estabelecimentoService.removerSabor(id, codigoAcesso, idSabor);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("{id}/sabores")
    public ResponseEntity<List<SaborGetRequestDTO>> listarSabores(
            @PathVariable Long id) {
        List<SaborGetRequestDTO> sabores = estabelecimentoService.listarSabores(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(sabores);
    }

    @GetMapping("{id}/saboresTipo")
    public ResponseEntity<List<SaborGetRequestDTO>> listarSaboresByTipo(
            @PathVariable Long id,
            @RequestParam(value = "tipo") TIPO_SABOR tipo) {

        List<SaborGetRequestDTO> sabores = estabelecimentoService.listarSaboresPorTipo(id, tipo);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(sabores);
    }

    @PostMapping("{id}/entregadores")
    public ResponseEntity<EntregadorGetRequestDTO> associarEntregador(
            @PathVariable Long id,
            @RequestParam String codigoAcesso,
            @RequestParam Long idEntregador,
            @RequestBody @Valid AssociaEntregadorPostPutRequestDTO dto)  {

        EntregadorGetRequestDTO entregador = estabelecimentoService.associarEntregador(id, codigoAcesso, dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entregador);
    }


}
