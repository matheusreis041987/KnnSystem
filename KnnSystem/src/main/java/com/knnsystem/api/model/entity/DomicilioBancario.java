package com.knnsystem.api.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "domicilio_bancario", schema = "sch_contratos")
@Getter
@Setter
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
	@Enumerated(EnumType.STRING)
	private StatusGeral statusDomicilio;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_id_fornecedor", referencedColumnName = "id")
	private Fornecedor fornecedor;

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
        return this.idDomicilio == d.idDomicilio && this.agencia.equals(d.agencia)
                && this.banco.equals(d.banco) && this.contaCorrente.equals(d.contaCorrente)
				&& this.pix.equals(d.pix);
		
	}
	

}
