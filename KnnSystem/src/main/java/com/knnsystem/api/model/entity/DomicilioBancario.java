package com.knnsystem.api.model.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "domicilio_bancario", schema = "sch_contratos")
@SecondaryTable(name = "fornecedor", schema = "sch_contratos")
public class DomicilioBancario {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idDomicilio;
	
	@Column(name = "agencia")
	private String agencia;
	
	@Column(name = "conta")
	private String contaCorrente;
	
	@Column(name = "banco")
	private String banco;
	
	@Column(name = "chave_pix")
	private String pix;
	
	@Column(name = "status")
	private String statusDomicilio;
	
	@OneToOne
	@JoinColumn(name = "fk_id_fornecedor", table = "fornecedor", referencedColumnName = "id")
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agencia == null) ? 0 : agencia.hashCode());
		result = prime * result + ((banco == null) ? 0 : banco.hashCode());
		result = prime * result + ((contaCorrente == null) ? 0 : contaCorrente.hashCode());
		result = prime * result + ((fornecedor == null) ? 0 : fornecedor.hashCode());
		result = prime * result + idDomicilio;
		result = prime * result + ((pix == null) ? 0 : pix.hashCode());
		result = prime * result + ((statusDomicilio == null) ? 0 : statusDomicilio.hashCode());
		return result;
	}

	
	public boolean equals(DomicilioBancario d) {
		
		if (this.idDomicilio == d.idDomicilio && this.agencia == d.agencia 
				&& this.banco == d.banco && this.contaCorrente == d.contaCorrente && this.pix == d.pix) {
			return true;
		} else {
			return false;
		}
		
	}
	
	
	
	
}
