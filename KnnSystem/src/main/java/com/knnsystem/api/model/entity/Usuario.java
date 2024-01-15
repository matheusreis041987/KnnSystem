package com.knnsystem.api.model.entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table (name = "usuario", schema = "sch_pessoas" )
@Getter
@Setter
public class Usuario extends Pessoa implements UserDetails {

	@Column(name = "perfil")
	private String perfil;
	
	@Column(name = "dt_nasc")
	private LocalDate dataNascimento;
	
	@Column(name = "cargo")
	private String cargo;
	
	@Column(name = "senha")
	private String senha;

	public Usuario(){

	}

	public Usuario(Pessoa pessoa) {
		this.setId(pessoa.getId());
		this.setEmail(pessoa.getEmail());
		this.setNome(pessoa.getNome());
		this.setCpf(pessoa.getCpf());
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
