package com.knnsystem.api.dto;

import java.util.Date;

import com.knnsystem.api.model.entity.Contrato;
import com.knnsystem.api.model.entity.StatusPagamento;

public class PagamentoDTO {

	private int idPagamento;

	private Contrato idContrato;
	
	private Date dataPagamento;
	
	private boolean temAprovacao;
	
	private double valorPagamento;
	
	private double valorJuros;
	
	private String percJuros;
	
	private StatusPagamento statusPagamento;
	
	private double valorTotal;

	public int getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(int idPagamento) {
		this.idPagamento = idPagamento;
	}

	public Contrato getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Contrato idContrato) {
		this.idContrato = idContrato;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public boolean isTemAprovacao() {
		return temAprovacao;
	}

	public void setTemAprovacao(boolean temAprovacao) {
		this.temAprovacao = temAprovacao;
	}

	public double getValorPagamento() {
		return valorPagamento;
	}

	public void setValorPagamento(double valorPagamento) {
		this.valorPagamento = valorPagamento;
	}

	public double getValorJuros() {
		return valorJuros;
	}

	public void setValorJuros(double valorJuros) {
		this.valorJuros = valorJuros;
	}

	public String getPercJuros() {
		return percJuros;
	}

	public void setPercJuros(String percJuros) {
		this.percJuros = percJuros;
	}

	public StatusPagamento getStatusPagamento() {
		return statusPagamento;
	}

	public void setStatusPagamento(StatusPagamento statusPagamento) {
		this.statusPagamento = statusPagamento;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	
	
	
	
}
