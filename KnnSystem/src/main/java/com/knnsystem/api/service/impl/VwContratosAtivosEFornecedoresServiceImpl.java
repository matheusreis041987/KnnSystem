package com.knnsystem.api.service.impl;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.model.entity.VwContratosAtivosEFornecedores;
import com.knnsystem.api.model.repository.VwContratosAtivosEFornecedoresRepository;
import com.knnsystem.api.service.VwContratosAtivosEFornecedoresService;

@Service
public class VwContratosAtivosEFornecedoresServiceImpl implements VwContratosAtivosEFornecedoresService {

	private VwContratosAtivosEFornecedoresRepository repository;
	
	public VwContratosAtivosEFornecedoresServiceImpl (VwContratosAtivosEFornecedoresRepository repo) {
		this.repository = repo;
	}
	
	@Override
	@Transactional
	public List<VwContratosAtivosEFornecedores> buscar(
			VwContratosAtivosEFornecedores VwContratosAtivosEFornecedoresParm) {
		
		Example example = Example.of(VwContratosAtivosEFornecedoresParm);
		
		return repository.findAll(example);
	}

	

}
