package com.knnsystem.api.model.entity;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name = "fornecedor", schema = "sch_contratos")
@SecondaryTable(name = "responsavel", schema = "sch_contratos")
@EqualsAndHashCode
public class Fornecedor {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private int idFornecedor;
	
	@Column(name = "num_contr")
	@Getter
	private Long numControle;
	
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

	public void geraNumeroDeControle(){
		// Número de controle gerado pelo sistema
		this.numControle = ThreadLocalRandom
				.current()
				.nextLong(1000000000L, 9999999999L);
	}


	public boolean equals(Fornecedor f) {
        return this.cnpj.equals(f.cnpj) &&
                this.razaoSocial.equals(f.razaoSocial) &&
				this.numControle.equals(f.numControle);
	}
	
	
}
