package com.knnsystem.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;

public record FornecedorDTO (
		Integer id,

		@NotBlank(message = "razão social é obrigatório")
		String razaoSocial,

		@NotBlank(message = "CNPJ é obrigatório")
		@CNPJ(message = "CNPJ inválido")
		String cnpj,

		@NotNull(message = "Domicílio bancário é obrigatório")
		DomicilioBancarioDTO domicilioBancario,

		@NotNull(message = "responsável pelo fornecedor é obrigatório")
		ResponsavelDTO responsavel,

		@NotBlank(message = "endereço completo é obrigatório")
		String enderecoCompleto,

		@NotBlank(message = "natureza do serviço é obrigatório")
		String naturezaDoServico,

		@NotBlank(message = "e-mail é obrigatório")
		@Email(message = "e-mail inválido")
		String emailCorporativo


) {

}
