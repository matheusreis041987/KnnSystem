package com.knnsystem.api.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table (name = "secretaria", schema = "sch_pessoas" )
public class Secretaria extends Usuario {


	@Override
	public int hashCode() {
		return super.hashCode();
	}

	
	public boolean equals(Secretaria s) {
		return this.getCpf().equals(s.getCpf()) &&
				this.getNome().equals(s.getNome());
	}

	
	
}
