package com.knnsystem.api.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table (name = "proprietario", schema = "sch_pessoas")
@Getter
@Setter
@EqualsAndHashCode
public class Proprietario extends Pessoa {

	@Column(name = "num_rgi")
	private int registroImovel;

}
