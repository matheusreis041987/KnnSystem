package com.knnsystem.api.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record AutenticacaoEsqueciSenhaDTO (
        @NotNull
        @CPF(message = "CPF inválido")
        String cpf
){
}
