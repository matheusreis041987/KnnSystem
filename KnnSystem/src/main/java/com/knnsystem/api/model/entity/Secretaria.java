package com.knnsystem.api.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "secretaria", schema = "sch_pessoas" )
public class Secretaria extends ParticipanteContrato{

	@Id
	private String cpf;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	
	public boolean equals(Secretaria s) {
		return this.getCpf().equals(s.getCpf()) &&
				this.getNome().equals(s.getNome());
	}

	
	
}
