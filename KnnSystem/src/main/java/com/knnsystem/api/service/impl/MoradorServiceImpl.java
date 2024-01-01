package com.knnsystem.api.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.model.entity.Morador;
import com.knnsystem.api.model.repository.MoradorRepository;
import com.knnsystem.api.servic.MoradorService;

@Service
public class MoradorServiceImpl implements MoradorService {

	private MoradorRepository repository;
	
	public MoradorServiceImpl (MoradorRepository repo) {
		this.repository = repo;
	}
	
	@Override
	@Transactional
	public Morador salvar(Morador MoradorParm) {
	
		
		return repository.save(MoradorParm);
	}

	@Override
	@Transactional
	public Morador atualizar(Morador MoradorParm) {
	
		Objects.requireNonNull(MoradorParm.getId());
		
		return repository.save(MoradorParm);
	}

	@Override
	@Transactional
	public void deletar(Morador MoradorParm) {
		
		Objects.requireNonNull(MoradorParm.getId());
		
		repository.delete(MoradorParm);
		
	}

	@Override
	@Transactional
	public List<Morador> buscar(Morador MoradorParm) {
		
		Example example = Example.of(MoradorParm);
		return repository.findAll(example);
	}

	@Override
	@Transactional
	public Optional<Morador> consultarPorId(Integer idMorador) {
		
		
		return repository.findById(idMorador);
	}

}
