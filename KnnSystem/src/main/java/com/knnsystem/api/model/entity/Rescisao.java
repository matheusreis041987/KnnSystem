package com.knnsystem.api.model.entity;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
		this.contrato = contrato;
	}

	public void calcularRescisao () {
		
//		double dataRescisao =  Double.parseDouble(dtRescisao);
//		DateFormat formatoData = new SimpleDateFormat("dd-MM-yyyy");
//		String dataInicialString = formatoData.format(contrato.getVigenciaFinal());
//		double dataInicial = Double.parseDouble(dataInicialString);
//		double tempoMeses = (dataRescisao - dataInicial) / 30;
//		double percMulta = Double.parseDouble(contrato.getPercMulta());
//		valorRescisao = tempoMeses * contrato.getValorMensalAtual() * percMulta;
		this.valorRescisao = BigDecimal.ZERO;
	}

}
