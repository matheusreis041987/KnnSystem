package com.knnsystem.api.model.repository;

import com.knnsystem.api.model.entity.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

import com.knnsystem.api.model.entity.Pagamento;

import java.util.List;

public interface PagamentoRepository<T extends Pagamento> extends JpaRepository<T, Integer> {

    List<Pagamento> findAllByContrato(Contrato contrato);
}
