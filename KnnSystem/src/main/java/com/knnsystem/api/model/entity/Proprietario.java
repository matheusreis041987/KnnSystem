package com.knnsystem.api.model.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "tbl_proprietario", schema = "sch_pessoas")
public class Proprietario extends Pessoa {

	@Column(name = "num_rgi")
	private int registroImovel;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@OneToOne
	@Column(name = "pk_id")
	@JoinColumn(name = "pk_id")
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id_pessoa == null) ? 0 : id_pessoa.hashCode());
		result = prime * result + registroImovel;
		return result;
	}

	
	public boolean equals(Proprietario p) {
		
		if (this.id_pessoa == p.id_pessoa && this.registroImovel == p.registroImovel && this.getCpf() == p.getCpf()) {
			return true;
		} else {
			return false;
		}
		
	}
	
	
	
	
}
