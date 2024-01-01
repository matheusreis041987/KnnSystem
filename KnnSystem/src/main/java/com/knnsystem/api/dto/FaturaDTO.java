package com.knnsystem.api.dto;

import java.util.Date;

import com.knnsystem.api.model.entity.Pagamento;
import com.knnsystem.api.model.entity.StatusGeral;

public class FaturaDTO {

	private int idFatura;
	
	private int numero;
	
	private double valor;
	
	private Date vencimento;

	private StatusGeral StatusFatura;
	
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
	
	
}
