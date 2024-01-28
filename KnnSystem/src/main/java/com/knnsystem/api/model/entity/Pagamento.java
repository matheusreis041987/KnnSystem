package com.knnsystem.api.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DiscriminatorFormula;

@Entity
@Table(name = "pagamento", schema = "sch_financeiro" )
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("Case when contrato.fornecedor.domicilio_bancario.pix is not null then 'PagamentoPix' else 'PagamentoDeposito' end")
@SecondaryTable(name = "sch_financeiro_contratos", schema = "sch_financeiro")
@SecondaryTable(name = "sch_pessoas_usuario", schema = "sch_pessoas")
@Getter
@Setter
public abstract class Pagamento {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPagamento;

	@OneToOne
	@JoinColumn(name = "fk_contrato", table = "sch_financeiro.contratos", referencedColumnName = "id")
	private Contrato contrato;

	@Column(name = "data_hora")
	private LocalDate dataPagamento;

	@Column(name = "aprovacao")
	private boolean temAprovacao;

	@Column(name = "valor_pagamento")
	private BigDecimal valorPagamento;

	@Column(name = "valor_juros")
	private BigDecimal valorJuros;

	@Column(name = "pct_juros")
	private BigDecimal percJuros;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private StatusPagamento statusPagamento;

	@Transient
	private BigDecimal valorTotal;
	

	@OneToOne
	@JoinColumn(name = "fk_usuario", table = "sch_pessoas.usuario", referencedColumnName = "id")
	private Usuario usuario;

	public final void efetuarPagamento() {
		this.valorJuros = this.getValorPagamento().multiply(
			percJuros.multiply(new BigDecimal("0.01"))
		);
		this.valorTotal = this.getValorPagamento().add(
				valorJuros
		);
		efetuarOperacoesAuxiliaresEmPagamento();
	}

	protected abstract void efetuarOperacoesAuxiliaresEmPagamento();
	
}
