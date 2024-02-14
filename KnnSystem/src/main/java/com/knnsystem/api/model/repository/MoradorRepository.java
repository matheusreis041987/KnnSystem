package com.knnsystem.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import com.knnsystem.api.model.entity.Morador;

public interface MoradorRepository extends JpaRepository<Morador, Long>  {

    Optional<Morador> findByCpf(String cpf);

    List<Morador> findByCpfOrNome(String cpf, String nome);
}
