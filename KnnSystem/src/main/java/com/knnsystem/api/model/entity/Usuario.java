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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table (name = "usuario", schema = "sch_pessoas" )
@Getter
@Setter
public class Usuario implements UserDetails {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "cpf")
	@CPF
	private String cpf;

	@Column(name = "email")
	@Email
	private String email;

	@Column(name = "perfil")
	@Enumerated(EnumType.STRING)
	private Perfil perfil;
	
	@Column(name = "dt_nasc")
	@Setter
	private LocalDate dataNascimento;
	
	@Column(name = "cargo")
	@Enumerated(EnumType.STRING)
	private Cargo cargo;
	
	@Column(name = "senha")
	@Setter
	private String senha;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", referencedColumnName = "id")
	private Pessoa pessoa;

	@Transient
	private boolean ativo;

	public Usuario(){

	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
		this.perfil = this.cargo.getPerfil();
	}

	public Usuario(Pessoa pessoa) {
		this.pessoa = pessoa;
		this.id = this.pessoa.getId();
		this.email = this.pessoa.getEmail();
		this.nome = this.pessoa.getNome();
		this.cpf = this.pessoa.getCpf();
	}

	public boolean isAtivo(){
		return this.pessoa.isAtivo();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return List.of(new SimpleGrantedAuthority(this.perfil.getPapel()));
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
