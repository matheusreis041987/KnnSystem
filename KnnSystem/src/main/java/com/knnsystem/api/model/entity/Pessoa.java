package com.knnsystem.api.model.entity;

import java.util.Objects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "pessoa", schema = "sch_pessoas" )
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class Pessoa {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "cpf" )
	private String cpf;
	
	@Column(name = "email")	
	private String email;
	
	@Column (name = "status")
	@Enumerated(EnumType.STRING)
	private StatusGeral status;

	public Pessoa(){
		this.status = StatusGeral.ATIVO;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf, email, id, nome, status);
	}

	public boolean equals(Pessoa p) {
		return this.cpf.equals(p.getCpf()) &&
				this.nome.equals(p.getNome());

	}
	
	
	

}
