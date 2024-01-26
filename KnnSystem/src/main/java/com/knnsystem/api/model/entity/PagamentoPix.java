package com.knnsystem.api.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
public class PagamentoPix extends Pagamento {

	@Transient
	@Getter
	@Setter
	private String chavePix;

	@Override
	protected void efetuarOperacoesAuxiliaresEmPagamento() {

	}
	
}
