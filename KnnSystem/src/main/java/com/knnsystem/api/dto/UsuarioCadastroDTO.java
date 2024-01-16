package com.knnsystem.api.dto;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record UsuarioCadastroDTO(
        String nome,

        @CPF
        String cpf,

        String telefone,

        @Email
        String email,

        LocalDate dataNascimento,

        String cargo,

        String senhaProvisoria
) {

}
