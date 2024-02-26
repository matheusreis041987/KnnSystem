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
@DiscriminatorFormula("Case when chave_pix is not null then 'PagamentoPix' else 'PagamentoDeposito' end")
@Getter
@Setter
public abstract class Pagamento {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPagamento;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_contrato", referencedColumnName = "id")
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

	@Column(name = "valor_total")
	private BigDecimal valorTotal;


	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fk_usuario", referencedColumnName = "id")
	private Usuario usuario;

	@Column(name = "chave_pix")
	private String pix;

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
		this.pix = contrato.getFornecedor().getDomicilioBancario().getPix();
	}
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
