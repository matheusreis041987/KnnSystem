package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.StatusGeral;

public class PessoaDTO {

	private int id;
	
	private String nome;
	
	private String cpf;
	
	private String email;
	
	private StatusGeral status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public StatusGeral getStatus() {
		return status;
	}

	public void setStatus(StatusGeral status) {
		this.status = status;
	}
	
	
	
	
}
