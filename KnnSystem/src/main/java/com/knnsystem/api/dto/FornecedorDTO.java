package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.DomicilioBancario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;

public record FornecedorDTO (
		Integer id,

		@NotBlank
		String razaoSocial,

		@CNPJ(message = "CNPJ inválido")
		String cnpj,

		@NotNull
		DomicilioBancarioDTO domicilioBancario,

		@NotNull
		ResponsavelDTO responsavel,

		@NotBlank
		String enderecoCompleto,

		@NotBlank
		String naturezaDoServico,

		@Email(message = "e-mail inválido")
		String emailCorporativo


) {

}
