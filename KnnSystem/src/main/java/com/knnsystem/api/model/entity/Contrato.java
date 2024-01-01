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
@Table(name = "tbl_contrato", schema = "sch_contratos")
public class Contrato {
	
	@Id
	@Column(name = "pk_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idContrato;
	
	@Column(name = "num_contrato")
	private String numContrato;
	
	@OneToMany
	@JoinColumn(name = "fk_id_fornecedor", table = "tbl_fornecedor", referencedColumnName = "pk_id")
	@Column(name = "fk_id_fornecedor", table = "tbl_fornecedor")
	private Fornecedor fornecedor;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private StatusGeral statusContrato;
	
	@Column(name = "pct_multa")
	private String percMulta;
	
	@ManyToOne
	@JoinColumn(name = "fk_cpf_sindico", table = "tbl_sindico", referencedColumnName = "pk_cpf")
	@Column(name = "fk_cpf_sindico", table = "tbl_sindico")
	private String cpfSindico;
	
	@Column(name = "fk_nome_sindico")
	private String nomeSindico;
	
	@Column(name = "fk_email_sindico")
	private String emailSindico;
	
	@Column(name = "fk_email_gestor")
	private String emailGestor;
	
	@ManyToOne
	@JoinColumn(name = "fk_cpf_gestor", table = "tbl_gestor", referencedColumnName = "pk_cpf")
	@Column(name = "fk_cpf_gestor", table = "tbl_gestor")
	private String cpfGestor;
	
	@Column(name = "fk_nome_gestor")
	private String nomeGestor;
	
	@Column(name = "vlr_mensal_atual")
	private double valorMensalAtual;
	
	@Column(name = "vlr_mensal_inicial")
	private double valorMensalInicial;
	
	@Column(name = "objeto")
	private String objetoContratual;
	
	@Column(name = "vigencia_inicial")
	private LocalDate vigenciaInicial;
	
	@Column(name = "vigencia_final")
	private LocalDate vigenciaFinal;
	
	@OneToOne
	@JoinColumn(name = "fk_id_rescisao", table = "tbl_rescisao", referencedColumnName = "pk_id")
	@Column(name = "fk_id_rescisao", table = "tbl_gestor")
	private Rescisao rescisao;

	public int getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(int idContrato) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpfGestor == null) ? 0 : cpfGestor.hashCode());
		result = prime * result + ((cpfSindico == null) ? 0 : cpfSindico.hashCode());
		result = prime * result + ((emailGestor == null) ? 0 : emailGestor.hashCode());
		result = prime * result + ((emailSindico == null) ? 0 : emailSindico.hashCode());
		result = prime * result + ((fornecedor == null) ? 0 : fornecedor.hashCode());
		result = prime * result + idContrato;
		result = prime * result + ((nomeGestor == null) ? 0 : nomeGestor.hashCode());
		result = prime * result + ((nomeSindico == null) ? 0 : nomeSindico.hashCode());
		result = prime * result + ((objetoContratual == null) ? 0 : objetoContratual.hashCode());
		result = prime * result + ((percMulta == null) ? 0 : percMulta.hashCode());
		result = prime * result + ((rescisao == null) ? 0 : rescisao.hashCode());
		result = prime * result + ((statusContrato == null) ? 0 : statusContrato.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valorMensalAtual);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(valorMensalInicial);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((vigenciaFinal == null) ? 0 : vigenciaFinal.hashCode());
		result = prime * result + ((vigenciaInicial == null) ? 0 : vigenciaInicial.hashCode());
		return result;
	}

	
	public boolean equals(Contrato c) {
		
		if (this.idContrato == c.idContrato && this.numContrato == c.numContrato) {
			return true;
		} else {
			return false;
		}
		
	}

	
	
	
	
}
