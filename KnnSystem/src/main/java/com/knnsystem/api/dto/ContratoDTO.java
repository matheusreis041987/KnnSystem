package com.knnsystem.api.dto;

import java.time.LocalDate;

import com.knnsystem.api.model.entity.Fornecedor;
import com.knnsystem.api.model.entity.Rescisao;
import com.knnsystem.api.model.entity.StatusGeral;

public class ContratoDTO {

	private Integer idContrato;
	
	private String numContrato;
	
	private Fornecedor fornecedor;
	
	private StatusGeral statusContrato;
	
	private String percMulta;
	
	private String cpfSindico;

	private String nomeSindico;

	private String emailSindico;
	
	private String emailGestor;

	private String cpfGestor;
	
	private String nomeGestor;
	
	private double valorMensalAtual;
	
	private double valorMensalInicial;
	
	private String objetoContratual;
	
	private LocalDate vigenciaInicial;

	private LocalDate vigenciaFinal;

	private Rescisao rescisao;

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	public String getNumContrato() {
		return numContrato;
	}

	public void setNumContrato(String numContrato) {
		this.numContrato = numContrato;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public StatusGeral getStatusContrato() {
		return statusContrato;
	}

	public void setStatusContrato(StatusGeral statusContrato) {
		this.statusContrato = statusContrato;
	}

	public String getPercMulta() {
		return percMulta;
	}

	public void setPercMulta(String percMulta) {
		this.percMulta = percMulta;
	}

	public String getCpfSindico() {
		return cpfSindico;
	}

	public void setCpfSindico(String cpfSindico) {
		this.cpfSindico = cpfSindico;
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

	public String getEmailGestor() {
		return emailGestor;
	}

	public void setEmailGestor(String emailGestor) {
		this.emailGestor = emailGestor;
	}

	public String getCpfGestor() {
		return cpfGestor;
	}

	public void setCpfGestor(String cpfGestor) {
		this.cpfGestor = cpfGestor;
	}

	public String getNomeGestor() {
		return nomeGestor;
	}

	public void setNomeGestor(String nomeGestor) {
		this.nomeGestor = nomeGestor;
	}

	public double getValorMensalAtual() {
		return valorMensalAtual;
	}

	public void setValorMensalAtual(double valorMensalAtual) {
		this.valorMensalAtual = valorMensalAtual;
	}

	public double getValorMensalInicial() {
		return valorMensalInicial;
	}

	public void setValorMensalInicial(double valorMensalInicial) {
		this.valorMensalInicial = valorMensalInicial;
	}

	public String getObjetoContratual() {
		return objetoContratual;
	}

	public void setObjetoContratual(String objetoContratual) {
		this.objetoContratual = objetoContratual;
	}

	public LocalDate getVigenciaInicial() {
		return vigenciaInicial;
	}

	public void setVigenciaInicial(LocalDate vigenciaInicial) {
		this.vigenciaInicial = vigenciaInicial;
	}

	public LocalDate getVigenciaFinal() {
		return vigenciaFinal;
	}

	public void setVigenciaFinal(LocalDate vigenciaFinal) {
		this.vigenciaFinal = vigenciaFinal;
	}

	public Rescisao getRescisao() {
		return rescisao;
	}

	public void setRescisao(Rescisao rescisao) {
		this.rescisao = rescisao;
	}
	
	
	
	
}
