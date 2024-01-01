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
@Table (name = "tbl_morador", schema = "sch_pessoas")
public class Morador extends Pessoa {

	@Column(name = "num_pat")
	private int numApt;
	
	@Column(name = "bloco")
	private String bloco;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@OneToOne
	@Column(name = "pk_id")
	@JoinColumn(name = "pk_id")
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bloco == null) ? 0 : bloco.hashCode());
		result = prime * result + ((id_pessoa == null) ? 0 : id_pessoa.hashCode());
		result = prime * result + numApt;
		return result;
	}

	
	public boolean equals(Morador m) {
		
		if (this.id_pessoa == m.id_pessoa && this.getNome() == m.getNome() && this.getCpf() == m.getCpf()) {
			return true;
		} else {
			return false;
		}
		
	}
	
	
	
	
}
