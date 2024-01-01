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
@Table(name = "tbl_fornecedor", schema = "sch_contratos")
public class Fornecedor {
	
	@Id
	@Column(name = "pk_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idFornecedor;
	
	@Column(name = "num_contr")
	private int numControle;
	
	@Column(name = "razao_social")
	private String razaoSocial;
	
	@Column(name = "cnpj")
	private String cnpj;
	
	@Column(name = "fk_cpf_responsavel")
	private String cpfResponsavel;
	
	@Column(name = "nome", table = "tbl_responsavel")
	private String nomeResponsavel;
	
	@Column(name = "email", table = "tbl_responsavel")	
	private String emailResponsavel;
	
	@Column(name = "email_corporativo")
	private String emailCorporativo;
	
	@Column(name = "natureza_servico")
	private String naturezaServico;
	
	@Column(name = "endereco")
	private String enderecoCompleto;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private StatusGeral StatusFornecedor;

	private DomicilioBancario domicilioBancario;

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

	public DomicilioBancario getDomicilioBancario() {
		return domicilioBancario;
	}

	public void setDomicilioBancario(DomicilioBancario domicilioBancario) {
		this.domicilioBancario = domicilioBancario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((StatusFornecedor == null) ? 0 : StatusFornecedor.hashCode());
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result + ((cpfResponsavel == null) ? 0 : cpfResponsavel.hashCode());
		result = prime * result + ((domicilioBancario == null) ? 0 : domicilioBancario.hashCode());
		result = prime * result + ((emailCorporativo == null) ? 0 : emailCorporativo.hashCode());
		result = prime * result + ((emailResponsavel == null) ? 0 : emailResponsavel.hashCode());
		result = prime * result + ((enderecoCompleto == null) ? 0 : enderecoCompleto.hashCode());
		result = prime * result + idFornecedor;
		result = prime * result + ((naturezaServico == null) ? 0 : naturezaServico.hashCode());
		result = prime * result + ((nomeResponsavel == null) ? 0 : nomeResponsavel.hashCode());
		result = prime * result + numControle;
		result = prime * result + ((razaoSocial == null) ? 0 : razaoSocial.hashCode());
		return result;
	}

	
	public boolean equals(Fornecedor f) {
		if (this.cnpj == f.cnpj && this.razaoSocial == f.razaoSocial && this.numControle == f.numControle) {
			return true;
		} else {
			return false;
		}
	}
	
	
}
