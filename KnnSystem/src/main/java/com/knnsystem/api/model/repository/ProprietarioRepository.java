package com.knnsystem.api.model.repository;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.jpa.repository.JpaRepository;

import com.knnsystem.api.model.entity.Proprietario;

import java.util.Optional;

public interface ProprietarioRepository extends JpaRepository<Proprietario, Integer> {

    Optional<Proprietario> findByCpf(@CPF String cpf);
}
