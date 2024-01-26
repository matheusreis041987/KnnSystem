package com.knnsystem.api.model.repository;

import com.knnsystem.api.model.entity.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import com.knnsystem.api.model.entity.Contrato;

import java.util.List;
import java.util.Optional;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {

    Optional<Contrato> findByNumContrato(String numContrato);

    List<Contrato> findAllByFornecedor(Fornecedor fornecedor);

}
