package com.ufcg.psoft.commerce.controller;
import com.ufcg.psoft.commerce.dto.EntregadorGetDTO;
import com.ufcg.psoft.commerce.dto.EntregadorPostPutDTO;
import com.ufcg.psoft.commerce.service.entregador.EntregadorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entregadores")
public class EntregadorController {

    @Autowired
    private EntregadorServiceImpl entregadorService;

    @PostMapping
    public ResponseEntity<EntregadorGetDTO> criarEntregador(@RequestBody EntregadorPostPutDTO entregadorCreateDTO) {
        EntregadorGetDTO response = entregadorService.criarEntregador(entregadorCreateDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntregadorGetDTO> editarEntregador(@PathVariable Long id, @RequestBody EntregadorPostPutDTO entregadorUpdateDTO) {
        EntregadorGetDTO response = entregadorService.editarEntregador(id, entregadorUpdateDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<EntregadorGetDTO> aprovarEntregador(@PathVariable Long id, @RequestParam String codigoAcessoEstabelecimento) {
        EntregadorGetDTO response = entregadorService.aprovarEntregador(id, codigoAcessoEstabelecimento);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerEntregador(@PathVariable Long id, @RequestParam String codigoAcessoEstabelecimento) {
        entregadorService.removerEntregador(id, codigoAcessoEstabelecimento);
        return ResponseEntity.noContent().build();
    }
}


