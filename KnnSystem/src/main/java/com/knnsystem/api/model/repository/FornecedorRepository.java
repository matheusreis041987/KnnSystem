package com.knnsystem.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.knnsystem.api.model.entity.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {

}
