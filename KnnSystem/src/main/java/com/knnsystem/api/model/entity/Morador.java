package com.knnsystem.api.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "morador", schema = "sch_pessoas")
@Getter
@Setter
@EqualsAndHashCode
public class Morador extends Pessoa {

	@Column(name = "num_apt")
	private int numApt;
	
	@Column(name = "bloco")
	private String bloco;

	
}
