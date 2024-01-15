package com.knnsystem.api.model.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagamento", schema = "sch_financeiro" )
public class PagamentoDeposito extends Pagamento {

	
	private String agenciaPg;
	
	private String bancoPg;
	
	private String contaPg;
	
	
	
	@Override
	public double efetuarPagamento() {
		
		double percentualJuros = Double.parseDouble(this.getPercJuros());
		double valorJuros = this.getIdPagamento() * percentualJuros;
		double valorTotal = this.getIdPagamento() + valorJuros;
		// chamada API banco do Brasil para pagamento em conta corrente.
		
		return valorTotal;
	}



	public String getAgenciaPg() {
		return agenciaPg;
	}



	public void setAgenciaPg(String agenciaPg) {
		this.agenciaPg = agenciaPg;
	}



	public String getBancoPg() {
		return bancoPg;
	}



	public void setBancoPg(String bancoPg) {
		this.bancoPg = bancoPg;
	}



	public String getContaPg() {
		return contaPg;
	}



	public void setContaPg(String contaPg) {
		this.contaPg = contaPg;
	}

	
	
}
