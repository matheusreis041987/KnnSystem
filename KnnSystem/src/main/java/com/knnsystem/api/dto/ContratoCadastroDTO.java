package com.knnsystem.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ContratoCadastroDTO(

		Long id,
		@NotBlank
		String numeroContrato,

		@NotNull
		Long numeroControleFornecedor,

		@NotNull
		LocalDate vigenciaInicial,

		@NotNull
		LocalDate vigenciaFinal,

		@NotNull
		BigDecimal valorMensalAtual,

		@NotNull
		BigDecimal valorMensalInicial,

		@NotNull
		String objetoContratual,

		@NotNull
		GestorDTO gestor,

		@Email(message = "e-mail inválido")
		String emailSindico,

		@NotNull
		BigDecimal percentualMulta
) {

}
