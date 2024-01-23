package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.Fornecedor;
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
		String emailCorporativo,

		Long numeroControle


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
				fornecedor.getNumControle()
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
		fornecedor.geraNumeroDeControle();

		return fornecedor;

	}
}
