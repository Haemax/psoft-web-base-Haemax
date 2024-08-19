package com.ufcg.psoft.commerce.repository;

import com.ufcg.psoft.commerce.model.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long> {
    Optional<Estabelecimento> findByCodigoAcesso(String codigoAcesso);
}

