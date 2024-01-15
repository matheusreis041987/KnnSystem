package com.knnsystem.api.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sindico", schema = "sch_contratos")
public class Sindico extends ParticipanteContrato {

	@Id
	private String cpf;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	
	public boolean equals(Sindico s) {
		return this.getNome().equals(s.getNome()) &&
				this.getCpf().equals(s.getCpf());
	}

	
	
}
