package com.knnsystem.api.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fornecedor", schema = "sch_contratos")
@SecondaryTable(name = "responsavel", schema = "sch_contratos")
public class Fornecedor {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private int idFornecedor;
	
	@Column(name = "num_contr")
	@Getter
	private int numControle;
	
	@Column(name = "razao_social")
	@Getter
	@Setter
	private String razaoSocial;
	
	@Column(name = "cnpj")
	@Getter
	@Setter
	private String cnpj;

	@Transient
	@Getter
	private Responsavel responsavel;

	@Column(name = "fk_cpf_responsavel")
	@Getter
	private String cpfResponsavel;
	
	@Column(name = "nome", table = "responsavel")
	@Getter
	private String nomeResponsavel;
	
	@Column(name = "email", table = "responsavel")
	@Getter
	private String emailResponsavel;
	
	@Column(name = "email_corporativo")
	@Getter
	@Setter
	private String emailCorporativo;
	
	@Column(name = "natureza_servico")
	@Getter
	@Setter
	private String naturezaServico;
	
	@Column(name = "endereco")
	@Getter
	@Setter
	private String enderecoCompleto;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	@Getter
	@Setter
	private StatusGeral StatusFornecedor;

	@OneToOne
	@Getter
	@Setter
	private DomicilioBancario domicilioBancario;

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
		this.cpfResponsavel = responsavel.getCpf();
		this.nomeResponsavel = responsavel.getNome();
		this.emailResponsavel = responsavel.getEmail();
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
        return this.cnpj.equals(f.cnpj) &&
                this.razaoSocial.equals(f.razaoSocial) &&
				this.numControle == f.numControle;
	}
	
	
}
