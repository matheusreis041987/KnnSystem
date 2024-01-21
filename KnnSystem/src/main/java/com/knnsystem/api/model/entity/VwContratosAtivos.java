package com.knnsystem.api.model.entity;

import java.io.Serializable;
import java.util.Date;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Immutable
@Table(name = "vw_contratos_ativos", schema = "sch_contratos")
@Subselect("select uuid() as id, vca.* from vw_contratos_ativos vca")
@Getter
 public class VwContratosAtivos implements Serializable {

	@Id
	private String id;

	@Column(name = "num_contrato")
	private String numeroContrato;
	
	@Column(name = "vigencia_final")
	private Date vigencialFinal;
	
	@Column(name = "vigencia_inicial")
	private Date vigenciaInicial;
	
	@Column(name = "objeto")
	private String objeto;
	
	@Column(name = "vlr_mensal_inicial")
	private double valorMensalInicial;
	
	@Column(name = "vlr_mensal_atual")
	private double valorMensalAtual;
	
	@Column(name = "pct_multa")
	private String percMulta;
	
	@Column(name = "fk_nome_gestor")
	private String nomeGestor;
	
	@Column(name = "fk_email_gestor")
	private String emailGestor;
	
	@Column(name = "nome_sindico")
	private String nomeSindico;
	
	@Column(name = "email_sindico")
	private String emailSindico;

}
