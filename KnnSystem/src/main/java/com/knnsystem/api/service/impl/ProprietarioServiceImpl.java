package com.knnsystem.api.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.model.entity.Proprietario;
import com.knnsystem.api.model.repository.ProprietarioRepository;
import com.knnsystem.api.servic.ProprietarioService;

@Service
public class ProprietarioServiceImpl implements ProprietarioService {

	private ProprietarioRepository repository;
	
	public ProprietarioServiceImpl (ProprietarioRepository repo) {
		this.repository = repo;
	}
	
	
	@Override
	@Transactional
	public Proprietario salvar(Proprietario ProprietarioParm) {
		
		
		
		return repository.save(ProprietarioParm);
	}

	@Override
	@Transactional
	public Proprietario atualizar(Proprietario ProprietarioParm) {
		
		Objects.requireNonNull(ProprietarioParm.getId());
		
		return repository.save(ProprietarioParm);
	}

	@Override
	@Transactional
	public void deletar(Proprietario ProprietarioParm) {
		
		Objects.requireNonNull(ProprietarioParm.getId());
		repository.delete(ProprietarioParm);
		
		
	}

	@Override
	@Transactional
	public List<Proprietario> buscar(Proprietario ProprietarioParm) {
		
		Example example = Example.of(ProprietarioParm);
		
		return repository.findAll(example);
	}

	@Override
	@Transactional
	public Optional<Proprietario> consultarPorId(Integer idProprietario) {
		
		
		
		return repository.findById(idProprietario);
	}

}
