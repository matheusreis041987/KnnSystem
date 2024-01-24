package com.knnsystem.api.model.repository;

import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.data.jpa.repository.JpaRepository;

import com.knnsystem.api.model.entity.Fornecedor;

import java.util.List;
import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {

    Optional<Fornecedor> findByCnpj(@CNPJ String cnpj);

    List<Fornecedor> findByCnpjOrRazaoSocialOrNumControle(
            @CNPJ String cnpj, String razaoSocial, Long numControle);

    Optional<Fornecedor> findByCnpjAndRazaoSocialAndNumControle(
            @CNPJ String cnpj, String razaoSocial, Long numControle
    );

    Optional<Fornecedor> findByNumControle(Long numControle);
}
