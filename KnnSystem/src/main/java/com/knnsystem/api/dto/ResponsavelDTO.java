package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.Responsavel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record ResponsavelDTO(
        @CPF(message = "cpf inválido")
        String cpf,

        @NotBlank
        String nome,

        @Email(message = "e-mail inválido")
        String email,

        @NotBlank
        String telefone

) {
        public ResponsavelDTO(Responsavel responsavel) {
                this(
                        responsavel.getCpf(),
                        responsavel.getNome(),
                        responsavel.getEmail(),
                        responsavel.getTelefone()
                );
        }

        public Responsavel toModel() {
                Responsavel responsavel = new Responsavel();
                responsavel.setTelefone(telefone());
                responsavel.setNome(nome());
                responsavel.setEmail(email());
                responsavel.setCpf(cpf());

                return responsavel;
        }
}
