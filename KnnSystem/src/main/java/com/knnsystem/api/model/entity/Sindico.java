package com.knnsystem.api.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_sindico", schema = "sch_contratos")
public class Sindico extends Usuario {

	
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	
	public boolean equals(Sindico s) {
	
		if (this.getNome() == s.getNome() && this.getCpf() == s.getCpf()) {
			return true;
		} else {
			return false;
		}
	}

	
	
}
