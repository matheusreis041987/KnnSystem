package com.knnsystem.api.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.model.entity.Contrato;
import com.knnsystem.api.model.repository.ApartamentoRepository;
import com.knnsystem.api.model.repository.ContratoRepository;
import com.knnsystem.api.servic.ContratoService;

@Service
public class ContratoServiceImpl implements ContratoService {

	private ContratoRepository repository;
	
	public ContratoServiceImpl (ContratoRepository repo) {
		this.repository = repo;
	}
	
	@Override
	@Transactional
	public Contrato salvar(Contrato contratoParam) {
		
		return repository.save(contratoParam);
	}

	@Override
	@Transactional
	public Contrato atualizar(Contrato contratoParam) {
		
		Objects.requireNonNull(contratoParam.getIdContrato());
		
		return repository.save(contratoParam);
	}

	@Override
	@Transactional
	public void deletar(Contrato contratoParam) {
		
		Objects.requireNonNull(contratoParam.getIdContrato());
		repository.delete(contratoParam);
		
	}

	@Override
	@Transactional
	public List<Contrato> buscar(Contrato contratoParam) {
		
		Example example = Example.of(contratoParam);
		return repository.findAll(example);
	}

	@Override
	@Transactional
	public Optional<Contrato> consultarPorId(Integer idContrato) {
		
		
		return repository.findById(idContrato);
	}

}
