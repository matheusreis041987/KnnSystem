package com.knnsystem.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ContratoCadastroDTO(

		Long id,
		@NotBlank(message = "Número do contrato é obrigatório")
		String numeroContrato,

		@NotNull(message = "Número de controle do fornecedor é obrigatório")
		Long numeroControleFornecedor,

		@NotNull(message = "Vigência inicial é obrigatória")
		LocalDate vigenciaInicial,

		@NotNull(message = "Vigência final é obrigatória")
		LocalDate vigenciaFinal,

		@NotNull(message = "valor mensal atual é obrigatório")
		BigDecimal valorMensalAtual,

		@NotNull(message = "valor mensal inicial é obrigatório")
		BigDecimal valorMensalInicial,

		@NotNull(message = "objeto contratual é obrigatório")
		String objetoContratual,

		@NotNull(message = "gestor é obrigatório")
		GestorDTO gestor,

		@Email(message = "e-mail inválido")
		String emailSindico,

		@NotNull(message = "percentual de multa é obrigatório")
		BigDecimal percentualMulta
) {

}
