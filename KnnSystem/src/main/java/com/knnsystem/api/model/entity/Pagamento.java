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
@Table (name = "tbl_pagamento", schema = "sch_financeiro")
abstract public class Pagamento {

	@Id
	@Column(name = "pk_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idPagamento;
	
		
	@ManyToOne
	@JoinColumn(name = "pk_id", table = "sch_financeiro.tbl_contratos", referencedColumnName = "fk_contrato")
	@Column(name = "fk_contrato")
	private Contrato idContrato;
	
	@Column(name = "data_hora")
	private Date dataPagamento;
	
	@Column(name = "aprovacao")
	private boolean temAprovacao;
	
	@Column(name = "valor_pagamento")
	private double valorPagamento;
	
	@Column(name = "valor_juros")
	private double valorJuros;
	
	@Column(name = "pct_juros")
	private String percJuros;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private StatusPagamento statusPagamento;
	
	private double valorTotal;
	

	@ManyToOne
	@JoinColumn(name = "pk_id", table = "sch_pessoas.tbl_usuario", referencedColumnName = "fk_usuario")
	@Column(name = "fk_usuario")
	private Usuario usuario;
	
	
	public abstract double  efetuarPagamento(); 
		
		/*double percentualJuros = Double.parseDouble(percJuros);
		valorJuros = valorPagamento * percentualJuros;
		valorTotal = valorPagamento + valorJuros;
		return valorTotal;*/
		
	


	public double getIdPagamento() {
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


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataPagamento == null) ? 0 : dataPagamento.hashCode());
		
		result = prime * result + ((idContrato == null) ? 0 : idContrato.hashCode());
		long temp;
		temp = Double.doubleToLongBits(idPagamento);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((percJuros == null) ? 0 : percJuros.hashCode());
		result = prime * result + ((statusPagamento == null) ? 0 : statusPagamento.hashCode());
		result = prime * result + (temAprovacao ? 1231 : 1237);
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		temp = Double.doubleToLongBits(valorJuros);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(valorPagamento);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(valorTotal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}


	
	public boolean equals(Pagamento p) {
		
		if (this.idContrato == p.getIdContrato() && this.idPagamento == p.getIdPagamento() 
				&& this.dataPagamento == p.getDataPagamento()) {
			return true;
		} else {
			return false;
		}
		
	}
	
	
	
	
}
