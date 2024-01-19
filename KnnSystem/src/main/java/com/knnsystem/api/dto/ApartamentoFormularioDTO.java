package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.Apartamento;
import com.knnsystem.api.model.entity.Morador;
import com.knnsystem.api.model.entity.Proprietario;
import com.knnsystem.api.model.entity.StatusGeral;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record ApartamentoFormularioDTO(
		@NotNull(message = "número do apartamento deve ser preenchido")
		int numeroDoApartamento,
		@NotBlank(message = "bloco deve ser preenchido")
		String bloco,
		@NotBlank(message = "nome do proprietário deve ser preenchido")
		String nomeDoProprietario,
		@NotBlank(message = "nome do morador deve ser preenchido")
		String nomeDoMorador,
		@NotBlank(message = "telefone do proprietário deve ser preenchido")
		String telefoneDoProprietario,
		@NotBlank(message = "telefone do morador deve ser preenchido")
		String telefoneDoMorador,
		@NotBlank(message = "CPF do proprietário deve ser preenchido")
		@CPF(message = "CPF do proprietário inválido")
		String cpfDoProprietario,
		@NotBlank(message = "CPF do morador deve ser preenchido")
		@CPF(message = "CPF do morador inválido")
		String cpfDoMorador,
		@NotBlank(message = "e-mail do proprietário deve ser preenchido")
		@Email(message = "e-mail do proprietário inválido")
		String emailDoProprietario,
		@NotBlank(message = "e-mail do morador deve ser preenchido")
		@Email(message = "e-mail do morador inválido")
		String emailDoMorador,
		@NotNull(message = "metragem do imóvel deve ser preenchida")
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

	public Apartamento toModel(boolean isInclusao) {
		Morador morador = new Morador();
		morador.setCpf(cpfDoMorador());
		morador.setNome(nomeDoMorador());
		morador.setEmail(emailDoMorador());
		morador.setNumApt(numeroDoApartamento());
		morador.setBloco(bloco());

		Proprietario proprietario = new Proprietario();
		proprietario.setCpf(cpfDoProprietario());
		proprietario.setNome(nomeDoProprietario());
		proprietario.setEmail(emailDoProprietario());

		Apartamento apartamento = new Apartamento();
		apartamento.setMorador(morador);
		apartamento.setProprietario(proprietario);
		apartamento.setNumApt(numeroDoApartamento());
		apartamento.setBlocoApt(bloco());
		apartamento.setMetragem(metragemDoImovel());

		if (isInclusao){
			apartamento.setStatusApt(StatusGeral.ATIVO);
		}

		return apartamento;
	}
}
