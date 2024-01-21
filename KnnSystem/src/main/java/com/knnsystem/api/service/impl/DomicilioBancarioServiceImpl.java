package com.knnsystem.api.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.model.entity.DomicilioBancario;
import com.knnsystem.api.model.repository.DomicilioBancarioRepository;
import com.knnsystem.api.service.DomicilioBancarioService;

@Service
public class DomicilioBancarioServiceImpl implements DomicilioBancarioService {

	private DomicilioBancarioRepository repository;
	
	public DomicilioBancarioServiceImpl (DomicilioBancarioRepository repo) {
		this.repository = repo;
	}
	
	@Override
	@Transactional
	public DomicilioBancario salvar(DomicilioBancario domicilioParm) {
		
		
		return repository.save(domicilioParm);
	}

	@Override
	@Transactional
	public DomicilioBancario atualizar(DomicilioBancario domicilioParm) {
		
		Objects.requireNonNull(domicilioParm.getIdDomicilio());
		return repository.save(domicilioParm);
	}

	@Override
	@Transactional
	public void deletar(DomicilioBancario domicilioParm) {
		Objects.requireNonNull(domicilioParm.getIdDomicilio());
		repository.delete(domicilioParm);
		
	}

	@Override
	@Transactional
	public List<DomicilioBancario> buscar(DomicilioBancario domicilioParm) {
		
		Example example = Example.of(domicilioParm);
		return repository.findAll(example);
	}

	@Override
	@Transactional
	public Optional<DomicilioBancario> consultarPorId(Integer idDomicilio) {
		
		
		return repository.findById(idDomicilio);
	}

}
