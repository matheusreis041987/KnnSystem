package com.knnsystem.api.model.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.core.sym.Name;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "vw_contratos_ativos", schema = "sch_contratos")
 public class VwContratosAtivos {

	@Column(name = "num_contrato")
	private String numeroContrato;
	
	@Column(name = "vigencia_final")
	private Date vigencialFinal;
	
	@Column(name = "vigencia_inicial")
	private Date vigenciaInicial;
	
	@Column(name = "objeto")
	private String objeto;
	
	@Column(name = "vlr_mensal_inicial")
	private double valorMensalInicial;
	
	@Column(name = "vlr_mensal_atual")
	private double valorMensalAtual;
	
	@Column(name = "pct_multa")
	private String percMulta;
	
	@Column(name = "fk_nome_gestor")
	private String nomeGestor;
	
	@Column(name = "fk_email_gestor")
	private String emailGestor;
	
	@Column(name = "nome_sindico")
	private String nomeSindico;
	
	@Column(name = "email_sindico")
	private String emailSindico;

	public String getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(String numeroContrato) {
		this.numeroContrato = numeroContrato;
	}

	public Date getVigencialFinal() {
		return vigencialFinal;
	}

	public void setVigencialFinal(Date vigencialFinal) {
		this.vigencialFinal = vigencialFinal;
	}

	public Date getVigenciaInicial() {
		return vigenciaInicial;
	}

	public void setVigenciaInicial(Date vigenciaInicial) {
		this.vigenciaInicial = vigenciaInicial;
	}

	public String getObjeto() {
		return objeto;
	}

	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}

	public double getValorMensalInicial() {
		return valorMensalInicial;
	}

	public void setValorMensalInicial(double valorMensalInicial) {
		this.valorMensalInicial = valorMensalInicial;
	}

	public double getValorMensalAtual() {
		return valorMensalAtual;
	}

	public void setValorMensalAtual(double valorMensalAtual) {
		this.valorMensalAtual = valorMensalAtual;
	}

	public String getPercMulta() {
		return percMulta;
	}

	public void setPercMulta(String percMulta) {
		this.percMulta = percMulta;
	}

	public String getNomeGestor() {
		return nomeGestor;
	}

	public void setNomeGestor(String nomeGestor) {
		this.nomeGestor = nomeGestor;
	}

	public String getEmailGestor() {
		return emailGestor;
	}

	public void setEmailGestor(String emailGestor) {
		this.emailGestor = emailGestor;
	}

	public String getNomeSindico() {
		return nomeSindico;
	}

	public void setNomeSindico(String nomeSindico) {
		this.nomeSindico = nomeSindico;
	}

	public String getEmailSindico() {
		return emailSindico;
	}

	public void setEmailSindico(String emailSindico) {
		this.emailSindico = emailSindico;
	}
	
	
	
	
}
