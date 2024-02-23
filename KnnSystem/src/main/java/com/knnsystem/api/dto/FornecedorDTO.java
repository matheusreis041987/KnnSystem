package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.Fornecedor;
import com.knnsystem.api.model.entity.StatusGeral;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;

public record FornecedorDTO (
		Long id,

		@NotBlank(message = "razão social é obrigatório")
		String razaoSocial,

		@NotBlank(message = "CNPJ é obrigatório")
		@CNPJ(message = "CNPJ inválido")
		String cnpj,

		@NotNull(message = "Domicílio bancário é obrigatório")
				@Valid
		DomicilioBancarioDTO domicilioBancario,

		@NotNull(message = "responsável pelo fornecedor é obrigatório")
				@Valid
		ResponsavelDTO responsavel,

		@NotBlank(message = "endereço completo é obrigatório")
		String enderecoCompleto,

		@NotBlank(message = "natureza do serviço é obrigatório")
		String naturezaDoServico,

		@NotBlank(message = "e-mail é obrigatório")
		@Email(message = "e-mail inválido")
		String emailCorporativo,

		String numeroControle,

		String status


) {

	public FornecedorDTO(Fornecedor fornecedor) {
		this(
				fornecedor.getIdFornecedor(),
				fornecedor.getRazaoSocial(),
				fornecedor.getCnpj(),
				new DomicilioBancarioDTO(fornecedor.getDomicilioBancario()),
				new ResponsavelDTO(fornecedor.getResponsavel()),
				fornecedor.getEnderecoCompleto(),
				fornecedor.getNaturezaServico(),
				fornecedor.getEmailCorporativo(),
				fornecedor.getNumControle(),
				fornecedor.getStatusFornecedor().toString()
		);
	}

	public Fornecedor toModel(boolean isInclusao) {
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setCnpj(cnpj());
		fornecedor.setNaturezaServico(naturezaDoServico());
		fornecedor.setEmailCorporativo(emailCorporativo());
		fornecedor.setResponsavel(responsavel().toModel());
		fornecedor.setDomicilioBancario(domicilioBancario().toModel(isInclusao));
		fornecedor.setRazaoSocial(razaoSocial());
		fornecedor.setEnderecoCompleto(enderecoCompleto());

		if (isInclusao) {
			fornecedor.setStatusFornecedor(StatusGeral.ATIVO);
		}

		return fornecedor;

	}
}
