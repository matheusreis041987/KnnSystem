package com.knnsystem.api.service.impl;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.model.entity.VwContratosAtivos;
import com.knnsystem.api.model.repository.VwContratosAtivosRepository;
import com.knnsystem.api.service.VwContratosAtivosService;

@Service
public class VwContratosAtivosServiceImpl implements VwContratosAtivosService{

	private VwContratosAtivosRepository repository;
	
	@Override
	@Transactional
	public List<VwContratosAtivos> buscar(VwContratosAtivos VwContratosAtivosParm) {
		
		Example example = Example.of(VwContratosAtivosParm);
		
		return repository.findAll(example);
	}

}
