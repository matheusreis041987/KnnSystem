package com.knnsystem.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.knnsystem.api.model.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
