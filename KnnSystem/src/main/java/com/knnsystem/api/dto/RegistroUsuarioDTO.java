package com.knnsystem.api.dto;

import java.time.LocalDate;

public record RegistroUsuarioDTO(
        String nome,

        String cpf,

        String telefone,

        String email,

        LocalDate dataDeNascimento,

        String cargo,

        String senhaProvisoria
) {
}
