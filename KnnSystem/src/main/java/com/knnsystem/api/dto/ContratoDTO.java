package com.knnsystem.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.knnsystem.api.model.entity.Contrato;
import com.knnsystem.api.model.entity.Fornecedor;
import com.knnsystem.api.model.entity.Sindico;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ContratoDTO(

		Long id,

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
				@Valid
		GestorDTO gestor,

		@Email(message = "e-mail inválido")
		String emailSindico,

		@NotNull(message = "percentual de multa é obrigatório")
		BigDecimal percentualMulta
) {

	public ContratoDTO(Contrato contratoSalvo) {
		this(
				contratoSalvo.getIdContrato(),
				contratoSalvo.getNumContrato(),
				contratoSalvo.getFornecedor().getNumControle(),
				contratoSalvo.getVigenciaInicial(),
				contratoSalvo.getVigenciaFinal(),
				contratoSalvo.getValorMensalAtual(),
				contratoSalvo.getValorMensalInicial(),
				contratoSalvo.getObjetoContratual(),
				new GestorDTO(contratoSalvo.getGestor()),
				contratoSalvo.getSindico().getEmail(),
				contratoSalvo.getPercMulta()
		);
	}

	public Contrato toModel(
			boolean isInclusao,
			Fornecedor fornecedor,
			Sindico sindico) {
		Contrato contrato = new Contrato();
		contrato.setFornecedor(fornecedor);
		contrato.setSindico(sindico);
		contrato.setPercMulta(percentualMulta());
		contrato.setValorMensalInicial(valorMensalInicial());
		contrato.setValorMensalAtual(valorMensalAtual());
		contrato.setObjetoContratual(objetoContratual());
		contrato.setVigenciaInicial(vigenciaInicial());
		contrato.setVigenciaFinal(vigenciaFinal());

		return contrato;
	}
}
