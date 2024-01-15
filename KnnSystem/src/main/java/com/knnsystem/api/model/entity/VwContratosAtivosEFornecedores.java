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
@Table(name = "vw_contrato_ativos_fornecedores_domic_bancario", schema = "sch_contratos")
@Subselect("select uuid() as id, vcaf.* from vw_contrato_ativos_fornecedores_domic_bancario vcaf")
@Getter
public class VwContratosAtivosEFornecedores implements Serializable {

	@Id
	private String id;

	@Column(name = "num_contrato")
	private String numContrato;
	
	@Column(name = "objeto")
	private String objeto;
	
	@Column(name = "vigencia_inicial")
	private Date vigenciaInicial;
	
	@Column(name = "vigencia_final")
	private Date vigenciaFinal;
	
	@Column(name = "status_contrato")
	private String statusContrato;
	
	@Column(name = "fk_nome_gestor")
	private String nomeGestor;
	
	@Column(name = "vlr_mensal_inicial")
	private double valorMensalInicial;
	
	@Column(name = "vlr_mensal_atual")
	private double valorMensalAtual;
	
	@Column(name = "razao_social")
	private String razaoSocial;
	
	@Column(name = "cnpj")
	private String cnpj;
	
	@Column(name = "natureza_servico")
	private String NaturezaServico;
	
	@Column(name = "email_corporativo")
	private String emailCorporativo;
	
	@Column(name = "endereco")
	private String endereco;
	
	@Column(name = "status_fornecedor")
	private String statusFornecedor;
	
	@Column(name = "agencia")
	private String agencia;
	
	@Column(name = "conta")
	private String conta;
	
	@Column(name = "banco")
	private String banco;
	
	@Column(name = "chave_pix")
	private String chavePix;
	
	@Column(name = "status_conta")
	private String statusConta;

}
