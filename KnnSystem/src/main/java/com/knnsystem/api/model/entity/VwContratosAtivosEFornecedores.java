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
@Table(name = "vw_contrato_ativos_fornecedores_domic_bancario", schema = "sch_contratos")
public class VwContratosAtivosEFornecedores {

	@Column(name = "num_contrato")
	private String numContrato;
	
	@Column(name = "objeto")
	private String objeto;
	
	@Column(name = "vigencia_inicial")
	private Date vigenciaInicial;
	
	@Column(name = "vigencia_final")
	private Date vigenciaFinal;
	
	@Column(name = "status_contrato")
	private String statusContrato;
	
	@Column(name = "fk_nome_gestor")
	private String nomeGestor;
	
	@Column(name = "vlr_mensal_inicial")
	private double valorMensalInicial;
	
	@Column(name = "vlr_mensal_atual")
	private double valorMensalAtual;
	
	@Column(name = "razao_social")
	private String razaoSocial;
	
	@Column(name = "cnpj")
	private String cnpj;
	
	@Column(name = "natureza_servico")
	private String NaturezaServico;
	
	@Column(name = "email_corporativo")
	private String emailCorporativo;
	
	@Column(name = "endereco")
	private String endereco;
	
	@Column(name = "status_fornecedor")
	private String statusFornecedor;
	
	@Column(name = "agencia")
	private String agencia;
	
	@Column(name = "conta")
	private String conta;
	
	@Column(name = "banco")
	private String banco;
	
	@Column(name = "chave_pix")
	private String chavePix;
	
	@Column(name = "status_conta")
	private String statusConta;

	public String getNumContrato() {
		return numContrato;
	}

	public void setNumContrato(String numContrato) {
		this.numContrato = numContrato;
	}

	public String getObjeto() {
		return objeto;
	}

	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}

	public Date getVigenciaInicial() {
		return vigenciaInicial;
	}

	public void setVigenciaInicial(Date vigenciaInicial) {
		this.vigenciaInicial = vigenciaInicial;
	}

	public Date getVigenciaFinal() {
		return vigenciaFinal;
	}

	public void setVigenciaFinal(Date vigenciaFinal) {
		this.vigenciaFinal = vigenciaFinal;
	}

	public String getStatusContrato() {
		return statusContrato;
	}

	public void setStatusContrato(String statusContrato) {
		this.statusContrato = statusContrato;
	}

	public String getNomeGestor() {
		return nomeGestor;
	}

	public void setNomeGestor(String nomeGestor) {
		this.nomeGestor = nomeGestor;
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

	public String getNaturezaServico() {
		return NaturezaServico;
	}

	public void setNaturezaServico(String naturezaServico) {
		NaturezaServico = naturezaServico;
	}

	public String getEmailCorporativo() {
		return emailCorporativo;
	}

	public void setEmailCorporativo(String emailCorporativo) {
		this.emailCorporativo = emailCorporativo;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getStatusFornecedor() {
		return statusFornecedor;
	}

	public void setStatusFornecedor(String statusFornecedor) {
		this.statusFornecedor = statusFornecedor;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getChavePix() {
		return chavePix;
	}

	public void setChavePix(String chavePix) {
		this.chavePix = chavePix;
	}

	public String getStatusConta() {
		return statusConta;
	}

	public void setStatusConta(String statusConta) {
		this.statusConta = statusConta;
	}
	
	
	
}
