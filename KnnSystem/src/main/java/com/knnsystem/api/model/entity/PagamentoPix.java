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
@Table(name = "tbl_pagamento", schema = "sch_financeiro" )
public class PagamentoPix extends Pagamento {

	private String chavePix;

	@Override
	public double efetuarPagamento() {
		
		double percentualJuros = Double.parseDouble(this.getPercJuros());
		double valorJuros = this.getIdPagamento() * percentualJuros;
		double valorTotal = this.getIdPagamento() + valorJuros;
		// chamada API banco do Brasil para pagamento em pix.
		
		return valorTotal;
	}

	public String getChavePix() {
		return chavePix;
	}

	public void setChavePix(String chavePix) {
		this.chavePix = chavePix;
	}
	
	
	
	
}
