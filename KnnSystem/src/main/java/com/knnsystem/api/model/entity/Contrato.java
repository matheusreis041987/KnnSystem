package com.knnsystem.api.model.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "contrato", schema = "sch_contratos")
@SecondaryTable(name = "gestor", schema = "sch_contratos")
@SecondaryTable(name = "sindico", schema = "sch_contratos")
@SecondaryTable(name = "fornecedor", schema = "sch_contratos")
@SecondaryTable(name = "rescisao", schema = "sch_contratos")
@Getter
@Setter
@EqualsAndHashCode
public class Contrato {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idContrato;

	@Column(name = "num_contrato")
	private String numContrato;

	@ManyToOne
	@JoinColumn(name = "fk_id_fornecedor", table = "fornecedor", referencedColumnName = "id")
	private Fornecedor fornecedor;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private StatusGeral statusContrato;

	@Column(name = "pct_multa")
	private String percMulta;

	@ManyToOne
	@JoinColumn(name = "fk_cpf_sindico", table = "sindico", referencedColumnName = "cpf")
	private Sindico sindico;

	@ManyToOne
	@JoinColumn(name = "fk_cpf_gestor", table = "gestor", referencedColumnName = "cpf")
	private Gestor gestor;

	@Column(name = "vlr_mensal_atual")
	private double valorMensalAtual;

	@Column(name = "vlr_mensal_inicial")
	private double valorMensalInicial;

	@Column(name = "objeto")
	private String objetoContratual;

	@Column(name = "vigencia_inicial")
	private LocalDate vigenciaInicial;

	@Column(name = "vigencia_final")
	private LocalDate vigenciaFinal;

	@OneToOne
	@JoinColumn(name = "fk_id_rescisao", table = "rescisao", referencedColumnName = "id")
	private Rescisao rescisao;

}
