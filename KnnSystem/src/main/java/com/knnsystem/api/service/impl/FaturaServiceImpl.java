package com.knnsystem.api.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.model.entity.Fatura;
import com.knnsystem.api.model.repository.FaturaRepository;
import com.knnsystem.api.service.FaturaService;

@Service
public class FaturaServiceImpl implements FaturaService  {

	private FaturaRepository repository;
	
	public FaturaServiceImpl (FaturaRepository repo) {
		this.repository = repo;
	}
	
	
	@Override
	@Transactional
	public Fatura salvar(Fatura FaturaParm) {
		
		
		return repository.save(FaturaParm);
	}

	@Override
	@Transactional
	public Fatura atualizar(Fatura FaturaParm) {
		
		Objects.requireNonNull(FaturaParm.getIdFatura());
		return repository.save(FaturaParm);
	}

	@Override
	@Transactional
	public void deletar(Fatura FaturaParm) {
		
		Objects.requireNonNull(FaturaParm.getIdFatura());
		repository.delete(FaturaParm);
		
	}

	@Override
	@Transactional
	public List<Fatura> buscar(Fatura FaturaParm) {
		
		Example example = Example.of(FaturaParm);
		return repository.findAll(example);
	}

	@Override
	@Transactional
	public Optional<Fatura> consultarPorId(Integer idFatura) {
		
		
		return repository.findById(idFatura);
	}

}
