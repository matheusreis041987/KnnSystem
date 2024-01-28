package com.knnsystem.api.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class PagamentoDeposito extends Pagamento {

	@Transient
	@Getter
	@Setter
	private String agenciaPg;

	@Transient
	@Getter
	@Setter
	private String bancoPg;

	@Transient
	@Getter
	@Setter
	private String contaPg;

	@Override
	protected void efetuarOperacoesAuxiliaresEmPagamento() {

	}
}
