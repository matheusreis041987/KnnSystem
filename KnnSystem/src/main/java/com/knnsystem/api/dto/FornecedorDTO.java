package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.StatusGeral;

public class FornecedorDTO {

	
private int idFornecedor;
	
	private int numControle;

	private String razaoSocial;
	
	private String cnpj;
	
	private String cpfResponsavel;

	private String nomeResponsavel;
		
	private String emailResponsavel;
	
	private String emailCorporativo;

	private String naturezaServico;
	
	private String enderecoCompleto;
	
	private StatusGeral StatusFornecedor;

	private DomicilioBancarioDTO domicilioBancario;

	public int getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(int idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	public int getNumControle() {
		return numControle;
	}

	public void setNumControle(int numControle) {
		this.numControle = numControle;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCpfResponsavel() {
		return cpfResponsavel;
	}

	public void setCpfResponsavel(String cpfResponsavel) {
		this.cpfResponsavel = cpfResponsavel;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public String getEmailResponsavel() {
		return emailResponsavel;
	}

	public void setEmailResponsavel(String emailResponsavel) {
		this.emailResponsavel = emailResponsavel;
	}

	public String getEmailCorporativo() {
		return emailCorporativo;
	}

	public void setEmailCorporativo(String emailCorporativo) {
		this.emailCorporativo = emailCorporativo;
	}

	public String getNaturezaServico() {
		return naturezaServico;
	}

	public void setNaturezaServico(String naturezaServico) {
		this.naturezaServico = naturezaServico;
	}

	public String getEnderecoCompleto() {
		return enderecoCompleto;
	}

	public void setEnderecoCompleto(String enderecoCompleto) {
		this.enderecoCompleto = enderecoCompleto;
	}

	public StatusGeral getStatusFornecedor() {
		return StatusFornecedor;
	}

	public void setStatusFornecedor(StatusGeral statusFornecedor) {
		StatusFornecedor = statusFornecedor;
	}

	public DomicilioBancarioDTO getDomicilioBancario() {
		return domicilioBancario;
	}

	public void setDomicilioBancario(DomicilioBancarioDTO domicilioBancario) {
		this.domicilioBancario = domicilioBancario;
	}
	
	
}
