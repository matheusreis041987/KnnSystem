package com.knnsystem.api.dto;

import java.util.Date;

public class VwContratosAtivosDTO {

	private String numeroContrato;
	
	private Date vigencialFinal;
	
	private Date vigenciaInicial;

	private String objeto;

	private double valorMensalInicial;
	
	private double valorMensalAtual;
	
	private String percMulta;

	private String nomeGestor;

	private String emailGestor;
	
	private String nomeSindico;
	
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
