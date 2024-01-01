package com.knnsystem.api.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table (name = "tbl_usuario", schema = "sch_pessoas" )
public class Secretaria extends Usuario {

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	
	public boolean equals(Secretaria s) {
		
		if (this.getCpf() == s.getCpf() && this.getNome() == s.getNome()) {
			return true;
		} else {
			return false;
		}
		
	}

	
	
}
