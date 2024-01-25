package com.knnsystem.api.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.model.entity.PagamentoDeposito;
import com.knnsystem.api.model.repository.PagamentoDepositoRepository;
import com.knnsystem.api.service.PagamentoDepositoService;

@Service
public class PagamentoDepositoServiceImpl implements PagamentoDepositoService {

	private PagamentoDepositoRepository repository;
	
	public PagamentoDepositoServiceImpl (PagamentoDepositoRepository repo) {
		this.repository = repo;
	}
	
	@Override
	@Transactional
	public PagamentoDeposito salvar(PagamentoDeposito PagamentoDepositoParm) {
		
		
		return repository.save(PagamentoDepositoParm);
	}

	@Override
	@Transactional
	public PagamentoDeposito atualizar(PagamentoDeposito PagamentoDepositoParm) {
		
		Objects.requireNonNull(PagamentoDepositoParm.getIdContrato());
		return repository.save(PagamentoDepositoParm);
	}

	@Override
	@Transactional
	public void deletar(PagamentoDeposito PagamentoDepositoParm) {
		
		Objects.requireNonNull(PagamentoDepositoParm.getIdContrato());
		repository.delete(PagamentoDepositoParm);
		
	}

	@Override
	@Transactional
	public List<PagamentoDeposito> buscar(PagamentoDeposito PagamentoDepositoParm) {
		
		Example example = Example.of(PagamentoDepositoParm);
		
		return repository.findAll(example);
	}

	@Override
	@Transactional
	public Optional<PagamentoDeposito> consultarPorId(Integer idPagamentoDeposito) {
		
		
		return repository.findById(idPagamentoDeposito);
	}

}
