package com.knnsystem.api.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.model.entity.Rescisao;
import com.knnsystem.api.model.repository.RescisaoRepository;
import com.knnsystem.api.servic.RescisaoService;

@Service
public class RescisaoServiceImpl implements RescisaoService {

	private RescisaoRepository repository;
	
	public RescisaoServiceImpl (RescisaoRepository repo) {
		this.repository = repo;
	}
	
	@Override
	@Transactional
	public Rescisao salvar(Rescisao RescisaoParm) {
		
		
		
		return repository.save(RescisaoParm);
	}

	@Override
	@Transactional
	public Rescisao atualizar(Rescisao RescisaoParm) {
		
		Objects.requireNonNull(RescisaoParm.getId());
		
		return repository.save(RescisaoParm);
	}

	@Override
	@Transactional
	public void deletar(Rescisao RescisaoParm) {
		
		Objects.requireNonNull(RescisaoParm.getId());
		repository.delete(RescisaoParm);
		
		
	}

	@Override
	@Transactional
	public List<Rescisao> buscar(Rescisao RescisaoParm) {
		
		Example example = Example.of(RescisaoParm);
		
		return repository.findAll(example);
	}

	@Override
	@Transactional
	public Optional<Rescisao> consultarPorId(Integer idRescisao) {
		
		
		
		return repository.findById(idRescisao);
	}

}
