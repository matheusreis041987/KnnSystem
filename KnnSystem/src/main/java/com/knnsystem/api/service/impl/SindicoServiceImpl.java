package com.knnsystem.api.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.model.entity.Sindico;
import com.knnsystem.api.model.repository.SindicoRepository;
import com.knnsystem.api.servic.SindicoService;

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
	public Sindico atualizar(Sindico SindicoParm) {
		
		Objects.requireNonNull(SindicoParm.getId());
		
		return repository.save(SindicoParm);
	}

	@Override
	@Transactional
	public void deletar(Sindico SindicoParm) {
	
		Objects.requireNonNull(SindicoParm.getId());
		repository.delete(SindicoParm);
		
		
	}

	@Override
	@Transactional
	public List<Sindico> buscar(Sindico SindicoParm) {
		
		Example example = Example.of(SindicoParm);
		
		return repository.findAll(example);
	}

	@Override
	@Transactional
	public Optional<Sindico> consultarPorId(Integer idSindico) {
		
		
		
		return repository.findById(idSindico);
	}

}
