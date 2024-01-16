package com.knnsystem.api.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.model.entity.Secretaria;
import com.knnsystem.api.model.repository.SecretariaRepository;
import com.knnsystem.api.service.SecretariaService;

@Service
public class SecretariaServiceImpl implements SecretariaService {

	private SecretariaRepository repository;
	
	public SecretariaServiceImpl (SecretariaRepository repo) {
		this.repository = repo;
	}
	
	@Override
	@Transactional
	public Secretaria salvar(Secretaria SecretariaParm) {
		
		
		
		return repository.save(SecretariaParm);
	}

	@Override
	@Transactional
	public Secretaria atualizar(Secretaria SecretariaParm) {
		
		Objects.requireNonNull(SecretariaParm.getCpf());
		
		return repository.save(SecretariaParm);
	}

	@Override
	@Transactional
	public void deletar(Secretaria SecretariaParm) {
		
		Objects.requireNonNull(SecretariaParm.getCpf());
		repository.delete(SecretariaParm);
		
		
	}

	@Override
	@Transactional
	public List<Secretaria> buscar(Secretaria SecretariaParm) {
		
		Example example = Example.of(SecretariaParm);
		
		return repository.findAll(example);
	}

	@Override
	@Transactional
	public Optional<Secretaria> consultarPorId(Integer idSecretaria) {
		
		
		
		return repository.findById(idSecretaria);
	}

	
	
	
}
