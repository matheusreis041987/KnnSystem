package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.Cargo;
import com.knnsystem.api.model.entity.StatusGeral;
import com.knnsystem.api.model.entity.Telefone;
import com.knnsystem.api.model.entity.Usuario;
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

        Telefone telefone,

        LocalDate dataNascimento,

        Cargo cargo,

        StatusGeral status

) {
        public UsuarioConsultaDTO(Usuario usuario) {
                this(usuario.getNome(), usuario.getCpf(),
                        usuario.getEmail(),
                        usuario.getPessoa().getTelefones().stream().findFirst().orElse(null),
                        usuario.getDataNascimento(), usuario.getCargo(),
                        usuario.getPessoa().getStatus());
        }
}
