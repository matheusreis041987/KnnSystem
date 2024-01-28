package com.knnsystem.api.model.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "email")	
	private String email;
	
	@Column (name = "status")
	@Enumerated(EnumType.STRING)
	private StatusGeral status;

	@OneToOne(mappedBy = "pessoa")
	private Usuario usuario;

	@OneToMany(mappedBy = "pessoa")
	private Set<Telefone> telefones = new HashSet<>();

	public Pessoa(){
		this.status = StatusGeral.ATIVO;
	}

	public boolean isAtivo() {
		return this.status.equals(StatusGeral.ATIVO);

	}

	public void adicionaTelefone(Telefone telefone){
		telefones.add(telefone);
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
