package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.Morador;
import com.knnsystem.api.model.entity.Proprietario;
import com.knnsystem.api.model.entity.StatusGeral;

public class ApartamentoDTO {

	private Integer idApartamento;
	
	private Morador morador;
	
	private Proprietario proprietario;
	
	private int numApt;
	
	private String blocoApt;

	private int metragem;
	
	private StatusGeral statusApt;

	public Integer getIdApartamento() {
		return idApartamento;
	}

	public void setIdApartamento(Integer idApartamento) {
		this.idApartamento = idApartamento;
	}

	public Morador getMorador() {
		return morador;
	}

	public void setMorador(Morador morador) {
		this.morador = morador;
	}

	public Proprietario getProprietario() {
		return proprietario;
	}

	public void setProprietario(Proprietario proprietario) {
		this.proprietario = proprietario;
	}

	public int getNumApt() {
		return numApt;
	}

	public void setNumApt(int numApt) {
		this.numApt = numApt;
	}

	public String getBlocoApt() {
		return blocoApt;
	}

	public void setBlocoApt(String blocoApt) {
		this.blocoApt = blocoApt;
	}

	public int getMetragem() {
		return metragem;
	}

	public void setMetragem(int metragem) {
		this.metragem = metragem;
	}

	public StatusGeral getStatusApt() {
		return statusApt;
	}

	public void setStatusApt(StatusGeral statusApt) {
		this.statusApt = statusApt;
	}
	
	
}
