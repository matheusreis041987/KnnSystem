package com.knnsystem.api.model.repository;

import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import com.knnsystem.api.model.entity.Sindico;

import java.util.Optional;

public interface SindicoRepository extends JpaRepository<Sindico, Integer> {


    Optional<Sindico> findByEmail(@Email String email);
}
