package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.Cargo;
import com.knnsystem.api.model.entity.StatusGeral;
import com.knnsystem.api.model.entity.Usuario;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record UsuarioConsultaDTO(
        String nome,

        @CPF
        String cpf,

        @Email
        String email,

        String telefone,

        LocalDate dataNascimento,

        Cargo cargo,

        StatusGeral status,

        Long id

) {
        public UsuarioConsultaDTO(Usuario usuario) {
                this(usuario.getNome(), usuario.getCpf(),
                        usuario.getEmail(),
                        usuario.getPessoa().getTelefonePrincipal().toString(),
                        usuario.getDataNascimento(), usuario.getCargo(),
                        usuario.getPessoa().getStatus(),
                        usuario.getId());
        }
}
