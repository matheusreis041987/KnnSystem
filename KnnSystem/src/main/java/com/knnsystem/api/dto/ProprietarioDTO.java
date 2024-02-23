package com.knnsystem.api.dto;


import com.knnsystem.api.model.entity.Proprietario;
import com.knnsystem.api.model.entity.StatusGeral;
import com.knnsystem.api.model.entity.Telefone;
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
		String email,

		String status
) {


	public ProprietarioDTO(Proprietario proprietario) {
		this (
				proprietario.getId(),
				proprietario.getRegistroImovel(),
				proprietario.getNome(),
				proprietario.getTelefonePrincipal() != null ? proprietario.getTelefonePrincipal().toString() : "",
				proprietario.getCpf(), proprietario.getEmail(),
				proprietario.getStatus().toString()
		);
	}

	public Proprietario toModel(boolean isInclusao) {
		Proprietario proprietario = new Proprietario();
		proprietario.setCpf(cpf());
		proprietario.setEmail(email());
		proprietario.setNome(nome());
		proprietario.setRegistroImovel(registroImovel());
		Telefone telefone = new Telefone();
		telefone.setNumero(telefone());
		proprietario.adicionaTelefone(telefone);

		if (isInclusao) {
			proprietario.setStatus(StatusGeral.ATIVO);
		}
		return proprietario;
	}
}
