package com.knnsystem.api.model.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
@Table(name = "rescisao", schema = "sch_contratos")
@Getter
@EqualsAndHashCode
public class Rescisao {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter
	private Long id;
	
	@Column(name = "causador")
	@Enumerated(EnumType.STRING)
	@Setter
	private CausadorRescisao causador;
	
	@Column(name = "valor")
	private BigDecimal valorRescisao;
	
	@Column(name = "dt_pgto")
	@Setter
	private LocalDate dtRescisao;

	@Column(name = "pct_multa")
	private BigDecimal pctMulta;

	@OneToOne
	private Contrato contrato;

	public Rescisao(){
		pctMulta = new BigDecimal("30.00");
	}

	public Rescisao(Contrato contrato){
		pctMulta = new BigDecimal("30.00");
		contrato.rescindir();
		this.contrato = contrato;
	}

	public void calcularRescisao () {

		var diasAntecipacaoTermino = new BigDecimal(DAYS.between(
				dtRescisao, contrato.getVigenciaFinal()));
		var mesesComerciaisAntecipados = diasAntecipacaoTermino.divide(
				new BigDecimal("30.00"), 15, RoundingMode.HALF_UP
		);
		this.valorRescisao = mesesComerciaisAntecipados
				.multiply(contrato.getValorMensalAtual())
				.multiply(this.pctMulta.multiply(new BigDecimal("0.01")))
				.setScale(2, RoundingMode.HALF_UP)
		;
	}

}
