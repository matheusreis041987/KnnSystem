package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.Pessoa;

public class ProprietarioDTO {
	
	private int registroImovel;

	private Pessoa id_pessoa;

	public int getRegistroImovel() {
		return registroImovel;
	}

	public void setRegistroImovel(int registroImovel) {
		this.registroImovel = registroImovel;
	}

	public Pessoa getId_pessoa() {
		return id_pessoa;
	}

	public void setId_pessoa(Pessoa id_pessoa) {
		this.id_pessoa = id_pessoa;
	}
	
	

}
