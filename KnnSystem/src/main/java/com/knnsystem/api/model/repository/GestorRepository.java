package com.knnsystem.api.model.repository;

import com.knnsystem.api.model.entity.Gestor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GestorRepository extends JpaRepository<Gestor,String> {

    Optional<Gestor> findByCpf(@CPF String cpf);
}
