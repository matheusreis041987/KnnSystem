package com.knnsystem.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.knnsystem.api.model.entity.Contrato;

import java.util.Optional;

public interface ContratoRepository extends JpaRepository<Contrato, Integer> {

    Optional<Contrato> findByNumContrato(String numContrato);
}
