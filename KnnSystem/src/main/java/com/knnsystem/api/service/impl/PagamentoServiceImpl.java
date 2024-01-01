package com.knnsystem.api.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.model.entity.Pagamento;
import com.knnsystem.api.model.repository.PagamentoRepository;
import com.knnsystem.api.servic.PagamentoService;

@Service
public class PagamentoServiceImpl implements PagamentoService {

	private PagamentoRepository repository;
	
	public PagamentoServiceImpl (PagamentoRepository repo) {
		this.repository = repo;
	}
	
	
	@Override
	@Transactional
	public Pagamento salvar(Pagamento PagamentoParm) {
		
		
		
		return repository.save(PagamentoParm);
	}

	@Override
	@Transactional
	public Pagamento atualizar(Pagamento PagamentoParm) {
		
		Objects.requireNonNull(PagamentoParm.getIdContrato());
		
		return repository.save(PagamentoParm);
	}

	@Override
	@Transactional
	public void deletar(Pagamento PagamentoParm) {
		
		Objects.requireNonNull(PagamentoParm.getIdContrato());
		repository.delete(PagamentoParm);
		
		
	}

	@Override
	@Transactional
	public List<Pagamento> buscar(Pagamento PagamentoParm) {
		
		Example example = Example.of(PagamentoParm);
		
		return repository.findAll(example);
	}

	@Override
	@Transactional
	public Optional<Pagamento> consultarPorId(Integer idPagamento) {
		
		
		
		return repository.findById(idPagamento);
	}

}
