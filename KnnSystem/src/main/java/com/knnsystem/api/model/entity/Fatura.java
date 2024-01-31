package com.knnsystem.api.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fatura", schema = "sch_financeiro")
@Getter
@Setter
public class Fatura {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idFatura;
	
	@Column(name = "numero")
	private Long numero;
	
	@Column(name = "valor")
	private BigDecimal valor;
	
	@Column(name = "vencimento")
	private LocalDate vencimento;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private StatusPagamento statusPagamento;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_pagamento", referencedColumnName = "id")
	private Pagamento pagamento;


	public boolean equals(Fatura f) {
		 return this.idFatura.equals(f.idFatura) && this.numero.equals(f.numero);
	}
	
	
	
}
