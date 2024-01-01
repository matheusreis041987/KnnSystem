package com.knnsystem.api.model.entity;

import java.time.LocalDate;
import java.util.Date;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_apartamento", schema = "sch_pessoas")
public class Apartamento {

	@Id
	@Column(name = "pk_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idApartamento;
	
	@ManyToOne
	@JoinColumn(name = "fk_morador")
	private Morador morador;
	
	@ManyToOne
	@JoinColumn(name = "fk_proprietario", table = "tbl_proprietario", referencedColumnName = "pk_id")
	@Column(name = "fk_proprietario", table = "tbl_proprietario")
	private Proprietario proprietario;
	
	@Column(name = "numero")
	private int numApt;
	
	@Column(name = "bloco")
	private String blocoApt;
	
	@Column(name = "metragem")
	private int metragem;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private StatusGeral statusApt;

	public int getIdApartamento() {
		return idApartamento;
	}

	public void setIdApartamento(int idApartamento) {
		this.idApartamento = idApartamento;
	}

	public Morador getMorador() {
		return morador;
	}

	public void setMorador(Morador morador) {
		morador = morador;
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

	
	
	public boolean equals(Apartamento a) {
		
		if (this.numApt == a.numApt && this.proprietario == a.proprietario 
				&& this.idApartamento == a.idApartamento && this.morador == a.morador) {
			return true;
		} else {
			return false;
		}
		
	}
	
	
	
	
	
}
