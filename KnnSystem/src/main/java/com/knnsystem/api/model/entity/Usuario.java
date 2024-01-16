package com.knnsystem.api.model.entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table (name = "usuario", schema = "sch_pessoas" )
@Getter
public class Usuario implements UserDetails {

	@Id
	@Column(name = "id")
	@Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "cpf")
	@CPF
	private String cpf;

	@Column(name = "email")
	@Email
	private String email;

	@Column(name = "perfil")
	@Setter
	private String perfil;
	
	@Column(name = "dt_nasc")
	@Setter
	private LocalDate dataNascimento;
	
	@Column(name = "cargo")
	@Setter
	private String cargo;
	
	@Column(name = "senha")
	@Setter
	private String senha;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", referencedColumnName = "id")
	private Pessoa pessoa;

	public Usuario(){

	}

	public Usuario(Pessoa pessoa) {
		this.pessoa = pessoa;
		this.id = this.pessoa.getId();
		this.email = this.pessoa.getEmail();
		this.nome = this.pessoa.getNome();
		this.cpf = this.pessoa.getCpf();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.getCpf();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
