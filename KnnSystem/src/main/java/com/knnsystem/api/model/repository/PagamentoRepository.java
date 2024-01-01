package com.knnsystem.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.knnsystem.api.model.entity.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

}
