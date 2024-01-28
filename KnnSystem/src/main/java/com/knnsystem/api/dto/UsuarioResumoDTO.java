package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.Usuario;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

public record UsuarioResumoDTO(

        Long id,
        String nome,

        @CPF
        String cpf,
        @Email
        String email

) {
    public UsuarioResumoDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getCpf(), usuario.getEmail());
    }
}
