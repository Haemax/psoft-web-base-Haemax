package com.ufcg.psoft.commerce.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Pizzas")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tamanho")
    private TAMANHO_PIZZA tamanho;

    @ManyToMany
    @JsonManagedReference
    private List<Sabor> sabores;

    public double calcularValor() {
        return sabores.stream()
                .mapToDouble(sabor -> {
                    if (tamanho == TAMANHO_PIZZA.GRANDE) {
                        return sabor.getValorGrande();
                    } else {
                        return sabor.getValorMedia();
                    }
                })
                .average()
                .orElse(0);
    }
}

