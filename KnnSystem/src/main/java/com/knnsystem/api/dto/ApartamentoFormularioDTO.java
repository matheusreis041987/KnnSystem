package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.Apartamento;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record ApartamentoFormularioDTO(
		@NotNull
		int numeroDoApartamento,
		@NotBlank
		String bloco,
		@NotBlank
		String nomeDoProprietario,
		@NotBlank
		String nomeDoMorador,
		@NotBlank
		String telefoneDoProprietario,
		@NotBlank
		String telefoneDoMorador,
		@CPF
		String cpfDoProprietario,
		@CPF
		String cpfDoMorador,
		@Email
		String emailDoProprietario,
		@Email
		String emailDoMorador,
		@NotNull
		int metragemDoImovel,

		int id) {

	public ApartamentoFormularioDTO(Apartamento apartamento){
		this(apartamento.getNumApt(), apartamento.getBlocoApt(),
				apartamento.getProprietario().getNome(),
				apartamento.getMorador().getNome(),
				apartamento.getProprietario().getTelefones().stream().findFirst().toString(),
				apartamento.getMorador().getTelefones().stream().findFirst().toString(),
				apartamento.getProprietario().getCpf(),
				apartamento.getMorador().getCpf(),
				apartamento.getProprietario().getEmail(),
				apartamento.getMorador().getEmail(),
				apartamento.getMetragem(),
				apartamento.getIdApartamento());

	}
}
