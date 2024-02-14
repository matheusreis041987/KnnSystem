package com.knnsystem.api.model.repository;

import com.knnsystem.api.model.entity.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import com.knnsystem.api.model.entity.DomicilioBancario;

import java.util.Optional;

public interface DomicilioBancarioRepository extends JpaRepository<DomicilioBancario, Integer> {


    Optional<DomicilioBancario> findByFornecedor(Fornecedor fornecedor);
}
