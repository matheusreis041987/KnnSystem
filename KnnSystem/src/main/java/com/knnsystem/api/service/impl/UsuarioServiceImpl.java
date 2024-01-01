package com.knnsystem.api.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.exceptions.ErroAutenticacao;
import com.knnsystem.api.exceptions.RegraNegocioException;
import com.knnsystem.api.model.entity.Usuario;
import com.knnsystem.api.model.repository.UsuarioRepository;
import com.knnsystem.api.servic.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private UsuarioRepository repository;
	
	public UsuarioServiceImpl (UsuarioRepository repo) {
		this.repository = repo;
	}
	
	@Override
	@Transactional
	public Usuario salvar(Usuario UsuarioParm) {
		
		
		
		return repository.save(UsuarioParm);
	}

	@Override
	@Transactional
	public Usuario atualizar(Usuario UsuarioParm) {
		
		Objects.requireNonNull(UsuarioParm.getId());
		
		return repository.save(UsuarioParm);
	}

	@Override
	@Transactional
	public void deletar(Usuario UsuarioParm) {
	
		Objects.requireNonNull(UsuarioParm.getId());
		repository.delete(UsuarioParm);
		
	}

	@Override
	@Transactional
	public List<Usuario> buscar(Usuario UsuarioParm) {
	
		Example example = Example.of(UsuarioParm);
		
		return repository.findAll(example);
	}

	@Override
	@Transactional
	public Optional<Usuario> consultarPorId(Integer idUsuario) {
	
		
		
		return repository.findById(idUsuario);
	}

	@Override
	public Usuario autentica(String email, String senha) {
		
		Optional<Usuario> usuario = repository.findByEmail(email);
		
		if(!usuario.isPresent()){
			throw new ErroAutenticacao("Usuario não encontrado para o email informado.");
		}
		if(!usuario.get().getSenha().equals(senha)){
			throw new ErroAutenticacao("Senha inválida.");
		}
		return usuario.get();
		
	}

	@Override
	public void validarEmail(String email) {
		
		boolean existe = repository.existsByEmail(email);
		if(existe) {
			throw new RegraNegocioException("Já existe um usuário cadastrado para este email");
		}
	}
		
		
	}


