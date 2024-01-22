package com.knnsystem.api.dto;

import jakarta.validation.constraints.Pattern;

public record AutenticacaoProvisoriaDTO(
        String cpf,

        String senhaProvisoria,
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "senha deve possuir no mínimo 8 posições, com letras e números")
        String novaSenha
) {
}
