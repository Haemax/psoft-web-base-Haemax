package com.ufcg.psoft.commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.commerce.model.Entregador;

import java.util.List;

public interface EntregadorRepository extends JpaRepository<Entregador, Long> {
    List<Entregador> findByEstabelecimentoId(Long estabelecimentoId);

}
