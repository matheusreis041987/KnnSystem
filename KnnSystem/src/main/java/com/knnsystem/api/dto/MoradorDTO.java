package com.knnsystem.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.relational.core.sql.In;

public record MoradorDTO (
		Integer id,

		@NotBlank(message = "nome do morador é obrigatório")
		String nome,

		@NotBlank(message = "CPF do morador é obrigatório")
		@CPF(message = "CPF inválido")
		String cpf,

		@NotBlank(message = "e-mail do morador é obrigatório")
		@Email(message = "e-mail inválido")
		String email,

		@NotBlank(message = "telefone do morador é obrigatório")
		String telefone,

		@NotNull(message = "número do apartamento do morador é obrigatório")
		Integer numeroDoApartamento,

		@NotBlank(message = "bloco do apartamento do morador é obrigatório")
		String blocoDoApartamento
		){

}
