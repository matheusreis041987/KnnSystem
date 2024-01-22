package com.knnsystem.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record ResponsavelDTO(
        @CPF(message = "cpf inválido")
        String cpf,

        @NotBlank
        String nome,

        @Email(message = "e-mail inválido")
        String email

) {
}
