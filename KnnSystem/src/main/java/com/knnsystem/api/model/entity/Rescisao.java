package com.knnsystem.api.model.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rescisao", schema = "sch_contratos")
@SecondaryTable(name = "contrato", schema = "sch_contratos")
@Getter
@Setter
@EqualsAndHashCode
public class Rescisao {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "causador")
	private String causador;
	
	@Column(name = "valor")
	private double valorRescisao;
	
	@Column(name = "dt_pgto")
	private String dtRescisao;
	
	@OneToOne
	private Contrato contrato;


	public String getPercentualMulta() {
		return this.contrato.getPercMulta();
	}
	
	public double CalcularRescisao () {
		
		double dataRescisao =  Double.parseDouble(dtRescisao);
		DateFormat formatoData = new SimpleDateFormat("dd-MM-yyyy");
		String dataInicialString = formatoData.format(contrato.getVigenciaFinal());
		double dataInicial = Double.parseDouble(dataInicialString);
		double tempoMeses = (dataRescisao - dataInicial) / 30;
		double percMulta = Double.parseDouble(contrato.getPercMulta());
		valorRescisao = tempoMeses * contrato.getValorMensalAtual() * percMulta; 
		
		return valorRescisao;
	}

}
