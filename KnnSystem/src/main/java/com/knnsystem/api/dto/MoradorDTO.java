package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.Morador;
import com.knnsystem.api.model.entity.StatusGeral;
import com.knnsystem.api.model.entity.Telefone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record MoradorDTO (
		Long id,

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

	public MoradorDTO(Morador morador) {
		this(
				morador.getId(),
				morador.getNome(),
				morador.getCpf(),
				morador.getEmail(),
				morador.getTelefones().stream().findFirst().orElse(new Telefone()).toString(),
				morador.getNumApt(),
				morador.getBloco()
				);
	}

	public Morador toModel(boolean isInclusao) {
		Morador morador = new Morador();
		morador.setCpf(cpf());
		morador.setNome(nome());
		morador.setEmail(email());
		morador.setBloco(blocoDoApartamento());
		morador.setNumApt(numeroDoApartamento());
		Telefone telefone = new Telefone();
		telefone.setNumero(telefone());
		morador.adicionaTelefone(telefone);

		if (isInclusao){
			morador.setStatus(StatusGeral.ATIVO);
		}

		return morador;
	}
}
