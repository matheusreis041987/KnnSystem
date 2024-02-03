package com.knnsystem.api.dto;


import com.knnsystem.api.model.entity.Proprietario;
import com.knnsystem.api.model.entity.StatusGeral;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record ProprietarioDTO (

		Long id,

		Integer registroImovel,

		@NotBlank(message = "nome do proprietário deve ser preenchido")
		String nome,
		@NotBlank(message = "telefone do proprietário deve ser preenchido")
		String telefone,
		@NotBlank(message = "CPF do proprietário deve ser preenchido")
		@CPF(message = "CPF do proprietário inválido")
		String cpf,
		@NotBlank(message = "e-mail do proprietário deve ser preenchido")
		@Email(message = "e-mail do proprietário inválido")
		String email
) {


	public ProprietarioDTO(Proprietario proprietario) {
		this (
				proprietario.getId(), proprietario.getRegistroImovel(),
				proprietario.getNome(), proprietario.getTelefones().stream().findFirst().toString(),
				proprietario.getCpf(), proprietario.getEmail()
		);
	}

	public Proprietario toModel(boolean isInclusao) {
		Proprietario proprietario = new Proprietario();
		proprietario.setCpf(cpf());
		proprietario.setEmail(email());
		proprietario.setNome(nome());
		proprietario.setRegistroImovel(registroImovel());

		if (isInclusao) {
			proprietario.setStatus(StatusGeral.ATIVO);
		}
		return proprietario;
	}
}
