package com.knnsystem.api.model.entity;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "apartamento", schema = "sch_pessoas")
@Getter
@Setter
@EqualsAndHashCode
public class Apartamento {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idApartamento;
	
	@OneToOne
	@JoinColumn(name = "fk_morador")
	private Morador morador;
	
	@ManyToOne
	@JoinColumn(name = "fk_proprietario")
	private Proprietario proprietario;
	
	@Column(name = "numero")
	private int numApt;
	
	@Column(name = "bloco")
	private String blocoApt;
	
	@Column(name = "metragem")
	private int metragem;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private StatusGeral statusApt;

}
