package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.Cargo;
import com.knnsystem.api.model.entity.StatusGeral;
import com.knnsystem.api.model.entity.Telefone;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.Set;

public record UsuarioConsultaDTO(
        String nome,

        @CPF
        String cpf,

        @Email
        String email,

        Set<Telefone> telefones,

        LocalDate dataNascimento,

        Cargo cargo,

        StatusGeral status

) {
}
