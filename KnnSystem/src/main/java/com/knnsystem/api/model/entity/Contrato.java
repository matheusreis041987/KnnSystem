package com.knnsystem.api.model.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import com.knnsystem.api.exceptions.RegraNegocioException;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "contrato", schema = "sch_contratos")
@Getter
@EqualsAndHashCode
public class Contrato {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter
	private Long idContrato;

	@Column(name = "num_contrato")
	private String numContrato;

	@OneToOne
	@JoinColumn(name = "fk_id_fornecedor", referencedColumnName = "id")
	@Setter
	private Fornecedor fornecedor;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	@Setter
	private StatusContrato statusContrato;

	@Column(name = "pct_multa")
	@Setter
	private BigDecimal percMulta;

	@OneToOne
	@JoinColumn(name = "fk_cpf_sindico", referencedColumnName = "cpf")
	@Setter
	private Sindico sindico;

	@OneToOne
	@JoinColumn(name = "fk_cpf_gestor", referencedColumnName = "cpf")
	@Setter
	private Gestor gestor;

	@Column(name = "vlr_mensal_atual")
	@Setter
	private BigDecimal valorMensalAtual;

	@Column(name = "vlr_mensal_inicial")
	@Setter
	private BigDecimal valorMensalInicial;

	@Column(name = "objeto")
	@Setter
	private String objetoContratual;

	@Column(name = "vigencia_inicial")
	private LocalDate vigenciaInicial;

	@Column(name = "vigencia_final")
	@Setter
	private LocalDate vigenciaFinal;

	@Column(name = "data_ultimo_reajuste")
	private LocalDate dataUltimoReajuste;

	@OneToOne
	@JoinColumn(name = "fk_id_rescisao", referencedColumnName = "id")
	@Setter
	private Rescisao rescisao;

	@Transient
	private  SituacaoContrato situacaoContrato;

	public void setVigenciaInicial(LocalDate vigenciaInicial) {
		this.vigenciaInicial = vigenciaInicial;
		this.dataUltimoReajuste = vigenciaInicial;
	}

	public Contrato(){
		this.setStatusContrato(StatusContrato.ATIVO);
		situacaoContrato = new SituacaoContratoAtivo();
		percMulta = new BigDecimal("30.00");
		// Número de controle gerado pelo sistema
		var numControle = ThreadLocalRandom
				.current()
				.nextLong(1000000000L, 9999999999L);
		this.numContrato = Long.toString(numControle);
	}

	public void inativar() {
		situacaoContrato.inativar(this);
		situacaoContrato = new SituacaoContratoInativo();
	}

	public void reajustar(BigDecimal ipcaAcumulado, LocalDate dataReajuste) {
		var dataBase = (dataUltimoReajuste == null) ? this.vigenciaInicial : this.dataUltimoReajuste;

		if (dataBase.plusYears(1).isAfter(dataReajuste)) {
			throw new RegraNegocioException("Erro - Data inválida. O reajuste é feito anualmente");
		}

		this.valorMensalAtual = this.valorMensalAtual.multiply(
				BigDecimal.ONE.add(
						ipcaAcumulado.divide(new BigDecimal("100"),
								RoundingMode.HALF_UP))
		);
		this.dataUltimoReajuste = dataReajuste;
	}
}
