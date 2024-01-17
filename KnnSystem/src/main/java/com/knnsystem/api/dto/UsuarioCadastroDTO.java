package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.*;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.crypto.password.PasswordEncoder;

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


        public Usuario toModel(PasswordEncoder passwordEncoder) {
                // Instancia pessoa para passar ao construtor de usuário
                // Status do usuário é ativo ao cadastrar
                Pessoa pessoa = new Pessoa();
                pessoa.setCpf(cpf());
                pessoa.setNome(nome());
                pessoa.setEmail(email());
                pessoa.setStatus(StatusGeral.ATIVO);
                Telefone telefone = new Telefone();
                telefone.setNumero(telefone());
                pessoa.adicionaTelefone(telefone);

                Usuario usuario = new Usuario(pessoa);
                usuario.setCargo(Cargo.valueOf(cargo()));
                usuario.setDataNascimento(dataNascimento());
                usuario.setSenha(passwordEncoder.encode(senhaProvisoria()));

                return usuario;
        }
}
