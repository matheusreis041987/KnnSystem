package com.knnsystem.api.model.repository;

import com.knnsystem.api.model.entity.Fornecedor;
import com.knnsystem.api.model.entity.StatusContrato;
import org.springframework.data.jpa.repository.JpaRepository;

import com.knnsystem.api.model.entity.Contrato;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {

    Optional<Contrato> findByNumContrato(String numContrato);

    List<Contrato> findAllByFornecedor(Fornecedor fornecedor);

    List<Contrato> findAllByStatusContratoAndGreaterThanEqualVigenciaFinal(StatusContrato statusContrato, LocalDate vigenciaFinal);

}
