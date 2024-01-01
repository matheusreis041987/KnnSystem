package com.knnsystem.api.model.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "tbl_pessoa", schema = "sch_pessoas" )
public class Pessoa {
	
	@Id
	@Column(name = "pk_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "cpf" )
	private String cpf;
	
	@Column(name = "email")	
	private String email;
	
	@Column (name = "status")
	@Enumerated(EnumType.STRING)
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

	@Override
	public int hashCode() {
		return Objects.hash(cpf, email, id, nome, status);
	}

	public boolean equals(Pessoa p) {
		
		if (this.cpf == p.cpf && this.nome == p.nome ) {
			return true;
		} else {
			return false;
		}
		
	}
	
	
	

}
