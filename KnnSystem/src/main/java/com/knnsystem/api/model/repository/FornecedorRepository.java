package com.knnsystem.api.model.repository;


import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.data.jpa.repository.JpaRepository;

import com.knnsystem.api.model.entity.Fornecedor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    Optional<Fornecedor> findByCnpj(@CNPJ String cnpj);

    @Query("select f from Fornecedor f where f.cnpj = ?1  or f.razaoSocial = ?2 or f.numControle = ?3")
    List<Fornecedor> findByCnpjOrRazaoSocialOrNumControle(
            @CNPJ String cnpj, String razaoSocial, String numControle);

    Optional<Fornecedor> findByCnpjAndRazaoSocialAndNumControle(
            @CNPJ String cnpj, String razaoSocial, String numControle
    );

    Optional<Fornecedor> findByNumControle(String numControle);

    Optional<Fornecedor> findByRazaoSocial(String razaoSocial);

}
