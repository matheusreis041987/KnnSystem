package com.knnsystem.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.knnsystem.api.model.entity.Apartamento;
import com.knnsystem.api.model.entity.Morador;
import com.knnsystem.api.model.entity.Proprietario;
import com.knnsystem.api.model.entity.StatusGeral;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record ApartamentoFormularioDTO(

		@NotNull(message = "morador é obrigatório")
		@Valid
		@JsonProperty("morador")
		MoradorDTO moradorDTO,

		@NotNull(message = "proprietário é obrigatório")
		@Valid
		@JsonProperty("proprietario")
		ProprietarioDTO proprietarioDTO,

		@NotNull(message = "metragem do imóvel deve ser preenchida")
		Integer metragemDoImovel,

		Long id) {

	public ApartamentoFormularioDTO(Apartamento apartamento){
		this(
				new MoradorDTO(apartamento.getMorador()),
				new ProprietarioDTO(apartamento.getProprietario()),
				apartamento.getMetragem(),
				apartamento.getIdApartamento()
		);

	}

	public Apartamento toModel(boolean isInclusao) {
		Morador morador = moradorDTO().toModel(isInclusao);
		Proprietario proprietario = proprietarioDTO().toModel(isInclusao);

		Apartamento apartamento = new Apartamento();
		apartamento.setMorador(morador);
		apartamento.setProprietario(proprietario);
		apartamento.setNumApt(morador.getNumApt());
		apartamento.setBlocoApt(morador.getBloco());
		apartamento.setMetragem(metragemDoImovel());

		if (isInclusao){
			apartamento.setStatusApt(StatusGeral.ATIVO);
		}

		return apartamento;
	}
}
