package com.knnsystem.api.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.model.entity.PagamentoPix;
import com.knnsystem.api.model.repository.PagamentoPixRepository;
import com.knnsystem.api.service.PagamentoPixService;

@Service
public class PagamentoPixServiceImpl implements PagamentoPixService {

	private PagamentoPixRepository repository;
	
	public PagamentoPixServiceImpl (PagamentoPixRepository repo) {
		this.repository = repo;
	}
	
	@Override
	@Transactional
	public PagamentoPix salvar(PagamentoPix PagamentoPixParm) {
		
		
		
		return repository.save(PagamentoPixParm);
	}

	@Override
	@Transactional
	public PagamentoPix atualizar(PagamentoPix PagamentoPixParm) {
		
		Objects.requireNonNull(PagamentoPixParm.getIdContrato());
		
		return repository.save(PagamentoPixParm);
	}

	@Override
	@Transactional
	public void deletar(PagamentoPix PagamentoPixParm) {
		
		Objects.requireNonNull(PagamentoPixParm.getIdContrato());
		repository.delete(PagamentoPixParm);
		
		
	}

	@Override
	@Transactional
	public List<PagamentoPix> buscar(PagamentoPix PagamentoPixParm) {
		
		Example example = Example.of(PagamentoPixParm);
		
		return repository.findAll(example);
	}

	@Override
	@Transactional
	public Optional<PagamentoPix> consultarPorId(Integer idPagamentoPix) {
		
		
		
		return repository.findById(idPagamentoPix);
	}

}
