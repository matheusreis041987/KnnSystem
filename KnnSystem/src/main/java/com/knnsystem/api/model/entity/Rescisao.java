package com.knnsystem.api.model.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.core.sym.Name;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_rescisao", schema = "sch_contratos")
public class Rescisao {

	@Id
	@Column(name = "pk_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "causador")
	private String causador;
	
	@Column(name = "valor")
	private double valorRescisao;
	
	@Column(name = "dt_pgto")
	private String dtRescisao;
	
		
	private Contrato contrato;
	
	@OneToOne
	@JoinColumn(name = "pct_multa", table = "tbl_contrato", referencedColumnName = "pct_multa")
	@Column(name = "pct_multa")
	private String percentualMulta = contrato.getPercMulta();
	
	
	public double CalcularRescisao () {
		
		double dataRescisao =  Double.parseDouble(dtRescisao);
		DateFormat formatoData = new SimpleDateFormat("dd-MM-yyyy");
		String dataInicialString = formatoData.format(contrato.getVigenciaFinal());
		double dataInicial = Double.parseDouble(dataInicialString);
		double tempoMeses = (dataRescisao - dataInicial) / 30;
		double percMulta = Double.parseDouble(contrato.getPercMulta());
		valorRescisao = tempoMeses * contrato.getValorMensalAtual() * percMulta; 
		
		return valorRescisao;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCausador() {
		return causador;
	}


	public void setCausador(String causador) {
		this.causador = causador;
	}


	public double getValorRescisao() {
		return valorRescisao;
	}


	public void setValorRescisao(double valorRescisao) {
		this.valorRescisao = valorRescisao;
	}


	public String getDtRescisao() {
		return dtRescisao;
	}


	public void setDtRescisao(String dtRescisao) {
		this.dtRescisao = dtRescisao;
	}


	public Contrato getContrato() {
		return contrato;
	}


	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}


	public String getPercentualMulta() {
		return percentualMulta;
	}


	public void setPercentualMulta(String percentualMulta) {
		this.percentualMulta = percentualMulta;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((causador == null) ? 0 : causador.hashCode());
		result = prime * result + ((contrato == null) ? 0 : contrato.hashCode());
		result = prime * result + ((dtRescisao == null) ? 0 : dtRescisao.hashCode());
		result = prime * result + id;
		result = prime * result + ((percentualMulta == null) ? 0 : percentualMulta.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valorRescisao);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}


	
	public boolean equals(Rescisao r) {
		
		if (this.id == r.id && this.contrato.getFornecedor() == r.contrato.getFornecedor()) {
			return true;
		} else {
			return false;
		}
		
		
	}
	
	
	
	
	
	
}
