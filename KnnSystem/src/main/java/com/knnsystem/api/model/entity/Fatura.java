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
@Table(name = "tbl_fatura", schema = "sch_financeiro")
public class Fatura {

	@Id
	@Column(name = "pk_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idFatura;
	
	@Column(name = "numero")
	private int numero;
	
	@Column(name = "valor")
	private double valor;
	
	@Column(name = "vencimento")
	private Date vencimento;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private StatusGeral StatusFatura;
	
	@OneToOne
	@JoinColumn(name = "pk_id", table = "tbl_pagamento", referencedColumnName = "fk_pagamento")
	@Column(name = "fk_usuario")
	private Pagamento pagamento;

	public int getIdFatura() {
		return idFatura;
	}

	public void setIdFatura(int idFatura) {
		this.idFatura = idFatura;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	public StatusGeral getStatusFatura() {
		return StatusFatura;
	}

	public void setStatusFatura(StatusGeral statusFatura) {
		StatusFatura = statusFatura;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((StatusFatura == null) ? 0 : StatusFatura.hashCode());
		result = prime * result + idFatura;
		result = prime * result + numero;
		result = prime * result + ((pagamento == null) ? 0 : pagamento.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((vencimento == null) ? 0 : vencimento.hashCode());
		return result;
	}

	
	public boolean equals(Fatura f) {
		if(this.idFatura == f.idFatura && this.numero == f.numero) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
}
