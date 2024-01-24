package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.Gestor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record GestorDTO(
        @CPF(message = "CPF inválido")
        String cpf,

        @NotBlank
        String nome,

        @Email(message = "e-mail inválido")
        String email
) {
        public GestorDTO(Gestor gestor) {
                this(
                        gestor.getCpf(),
                        gestor.getNome(),
                        gestor.getEmail()
                );
        }

        public Gestor toModel() {
                Gestor gestor = new Gestor();
                gestor.setCpf(cpf());
                gestor.setNome(nome());
                gestor.setEmail(email());
                return gestor;
        }
}
