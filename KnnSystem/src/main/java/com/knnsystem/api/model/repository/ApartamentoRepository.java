package com.knnsystem.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.knnsystem.api.model.entity.Apartamento;

public interface ApartamentoRepository extends JpaRepository<Apartamento, Integer> {

}
