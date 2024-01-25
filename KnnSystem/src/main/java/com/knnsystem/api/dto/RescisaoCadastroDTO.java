package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.CausadorRescisao;
import com.knnsystem.api.model.entity.Contrato;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RescisaoCadastroDTO(

		@NotBlank(message = "causador da rescisão é obrigatório")
		String causador,

		@NotNull
		LocalDate dataRescisao
) {

	public CausadorRescisao criaCausador() {
		try {
			return CausadorRescisao.valueOf(causador());
		} catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException("Causador deve ser 'FORNECEDOR' ou 'CONTRATANTE'");
		}
	}
}
