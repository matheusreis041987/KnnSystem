package com.knnsystem.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.knnsystem.api.model.entity.Apartamento;

import java.util.Optional;

public interface ApartamentoRepository extends JpaRepository<Apartamento, Integer> {

    Optional<Apartamento> findByNumAptAndBlocoApt(int numApt, String blocoApt);

}
