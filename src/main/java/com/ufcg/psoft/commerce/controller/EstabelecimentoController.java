package com.ufcg.psoft.commerce.controller;
import com.ufcg.psoft.commerce.dto.EstabelecimentoGetDTO;
import com.ufcg.psoft.commerce.dto.EstabelecimentoPostPutDTO;
import com.ufcg.psoft.commerce.service.estabelecimento.EstabelecimentoService;
import com.ufcg.psoft.commerce.service.estabelecimento.EstabelecimentoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estabelecimentos")
public class EstabelecimentoController {

    @Autowired
    private EstabelecimentoServiceImpl estabelecimentoService;

    @PostMapping
    public ResponseEntity<EstabelecimentoGetDTO> criarEstabelecimento(@RequestBody EstabelecimentoPostPutDTO estabelecimentoCreateDTO) {
        EstabelecimentoGetDTO response = estabelecimentoService.criarEstabelecimento(estabelecimentoCreateDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstabelecimentoGetDTO> editarEstabelecimento(@PathVariable Long id, @RequestBody EstabelecimentoPostPutDTO estabelecimentoUpdateDTO) {
        EstabelecimentoGetDTO response = estabelecimentoService.editarEstabelecimento(id, estabelecimentoUpdateDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerEstabelecimento(@PathVariable Long id, @RequestParam String codigoAcesso) {
        estabelecimentoService.removerEstabelecimento(id, codigoAcesso);
        return ResponseEntity.noContent().build();
    }
}
