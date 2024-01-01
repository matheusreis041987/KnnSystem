package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.Contrato;

public class RescisaoDTO {
	
	private int id;
	
	private String causador;
	
	private double valorRescisao;

	private String dtRescisao;
	
	private Contrato contrato;

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
	
	

}
