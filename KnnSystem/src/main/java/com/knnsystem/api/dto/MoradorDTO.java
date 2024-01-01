package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.Pessoa;

public class MoradorDTO {

	private int numApt;

	private String bloco;

	private Pessoa id_pessoa;

	public int getNumApt() {
		return numApt;
	}

	public void setNumApt(int numApt) {
		this.numApt = numApt;
	}

	public String getBloco() {
		return bloco;
	}

	public void setBloco(String bloco) {
		this.bloco = bloco;
	}

	public Pessoa getId_pessoa() {
		return id_pessoa;
	}

	public void setId_pessoa(Pessoa id_pessoa) {
		this.id_pessoa = id_pessoa;
	}
	
	
	
}
