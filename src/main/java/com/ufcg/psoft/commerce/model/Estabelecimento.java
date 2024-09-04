package com.ufcg.psoft.commerce.model;

import com.fasterxml.jackson.annotation.*;
import com.ufcg.psoft.commerce.exception.SaborNaoExisteException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Estabelecimentos")
public class Estabelecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("nome")
    @Column(nullable = false)
    private String nome;

    @JsonProperty("codigoAcesso")
    @Column(nullable = false)
    private String codigoAcesso;

    @OneToMany
    @JsonManagedReference
    @Builder.Default
    private List<Sabor> sabores = new ArrayList<Sabor>();

    public Sabor addSabor(Sabor sabor) {
        this.sabores.add(sabor);
        return sabor;
    }

    public Sabor getSabor(Long id) {
        return this.sabores.stream().filter(sabor -> sabor.getId().equals(id)).findFirst().orElseThrow(SaborNaoExisteException::new);
    }

    public Sabor updateSabor(Long id, Sabor saborAtualizado) {
        removeSabor(saborAtualizado.getId());
        addSabor(saborAtualizado);
        return saborAtualizado;
    }

    public void removeSabor(Long id) {
        this.sabores.remove(this.sabores.stream().filter
                        (sabor -> sabor.getId().equals(id)).findFirst()
                .orElseThrow(SaborNaoExisteException::new));
    }

    @OneToMany(mappedBy = "estabelecimento")
    @JsonManagedReference
    @Builder.Default
    private List<Entregador> entregadoresAutorizados = new ArrayList<Entregador>();

    public Entregador addEntregador(Entregador entregador) {
        this.entregadoresAutorizados.add(entregador);
        return entregador;
    }

    public Entregador getEntregador(Long id){
        return this.entregadoresAutorizados.stream().filter(entregador -> entregador.getId().equals(id)).findFirst().orElseThrow(SaborNaoExisteException::new);
    }
}
