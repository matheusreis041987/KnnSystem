package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.Fornecedor;

public class DomicilioBancarioDTO {


	private int idDomicilio;
	
	private String agencia;
	
	private String contaCorrente;
	
	private String banco;
	
	private String pix;
	
	private String statusDomicilio;

	private Fornecedor fornecedor;

	public int getIdDomicilio() {
		return idDomicilio;
	}

	public void setIdDomicilio(int idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getContaCorrente() {
		return contaCorrente;
	}

	public void setContaCorrente(String contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getPix() {
		return pix;
	}

	public void setPix(String pix) {
		this.pix = pix;
	}

	public String getStatusDomicilio() {
		return statusDomicilio;
	}

	public void setStatusDomicilio(String statusDomicilio) {
		this.statusDomicilio = statusDomicilio;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	
	
}
