package com.knnsystem.api.model.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "tbl_usuario", schema = "sch_pessoas" )
public class Usuario extends Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@OneToOne
	@Column(name = "pk_id")
	@JoinColumn(name = "pk_id")
	private Pessoa id_pessoa;
	
	@Column(name = "perfil")
	private String perfil;
	
	@Column(name = "dt_nasc")
	private LocalDate dataNascimento;
	
	@Column(name = "cargo")
	private String cargo;
	
	@Column(name = "senha")
	private String senha;

	public Pessoa getId_pessoa() {
		return id_pessoa;
	}

	public void setId_pessoa(Pessoa id_pessoa) {
		this.id_pessoa = id_pessoa;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cargo == null) ? 0 : cargo.hashCode());
		result = prime * result + ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		result = prime * result + ((id_pessoa == null) ? 0 : id_pessoa.hashCode());
		result = prime * result + ((perfil == null) ? 0 : perfil.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		return result;
	}

	
	public boolean equals(Usuario u) {
		
		if(this.getCpf() == u.getCpf() && this.cargo == u.cargo && this.senha == u.senha && this.cargo == u.cargo && 
				this.dataNascimento == u.dataNascimento ) {
			return true;
		} else {
			return false;
		}
		
	}
	
	
	


	
	
}
