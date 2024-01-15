package com.knnsystem.api.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.model.entity.Apartamento;
import com.knnsystem.api.model.repository.ApartamentoRepository;
import com.knnsystem.api.service.ApartamentoService;



@Service
public class ApartamentoServiceImpl implements ApartamentoService {

	private ApartamentoRepository repository;
	
	public ApartamentoServiceImpl (ApartamentoRepository repo) {
		this.repository = repo;
	}
	
	@Override
	@Transactional
	public Apartamento salvar(Apartamento apartamentoParam) {
		
		return repository.save(apartamentoParam);
	}

	@Override
	@Transactional
	public Apartamento atualizar(Apartamento apartamentoParm) {
		
		Objects.requireNonNull(apartamentoParm.getIdApartamento());
		
		return repository.save(apartamentoParm);
	}

	@Override
	@Transactional
	public void deletar(Apartamento apartamentoParm) {
		
		Objects.requireNonNull(apartamentoParm.getIdApartamento());
		repository.delete(apartamentoParm);
	}

	@Override
	@Transactional
	public List<Apartamento> buscar(Apartamento apartamentoParm) {
	
		Example example = Example.of(apartamentoParm);
		return repository.findAll(example);
	}

	@Override
	@Transactional
	public Optional<Apartamento> consultarPorId(Integer idApartamento) {
	
		return repository.findById(idApartamento);
	}

}
