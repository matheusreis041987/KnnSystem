package com.knnsystem.api.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.model.entity.Sindico;
import com.knnsystem.api.model.repository.SindicoRepository;
import com.knnsystem.api.service.SindicoService;

@Service
public class SindicoServiceImpl implements SindicoService {

	private SindicoRepository repository;
	
	public SindicoServiceImpl(SindicoRepository repo) {
		this.repository = repo;
	}
	
	@Override
	@Transactional
	public Sindico salvar(Sindico SindicoParm) {
		
		
		
		return repository.save(SindicoParm);
	}

	@Override
	@Transactional
	public Sindico atualizar(Sindico sindicoParm) {
		
		Objects.requireNonNull(sindicoParm.getCpf());
		
		return repository.save(sindicoParm);
	}

	@Override
	@Transactional
	public void deletar(Sindico sindicoParm) {
	
		Objects.requireNonNull(sindicoParm.getCpf());
		repository.delete(sindicoParm);
		
		
	}

	@Override
	@Transactional
	public List<Sindico> buscar(Sindico sindicoParm) {
		
		Example example = Example.of(sindicoParm);
		
		return repository.findAll(example);
	}

	@Override
	@Transactional
	public Optional<Sindico> consultarPorId(Integer idSindico) {
		
		
		
		return repository.findById(idSindico);
	}

}
