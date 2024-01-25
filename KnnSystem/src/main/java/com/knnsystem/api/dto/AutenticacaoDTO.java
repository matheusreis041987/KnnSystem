package com.knnsystem.api.dto;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

public record AutenticacaoDTO(
        @CPF(message = "CPF inválido")
        String cpf,

        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "senha deve possuir no mínimo 8 posições, com letras e números")
        String senha) {
}
