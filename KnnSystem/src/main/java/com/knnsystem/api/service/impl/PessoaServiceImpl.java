package com.knnsystem.api.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.model.entity.Pessoa;
import com.knnsystem.api.model.repository.PessoaRepository;
import com.knnsystem.api.service.PessoaService;

@Service
public class PessoaServiceImpl implements PessoaService {

	private PessoaRepository repository;
	
	public PessoaServiceImpl (PessoaRepository repo) {
		this.repository = repo;
	}
	
	@Override
	@Transactional
	public Pessoa salvar(Pessoa PessoaParm) {
		
		
		
		return repository.save(PessoaParm);
	}

	@Override
	@Transactional
	public Pessoa atualizar(Pessoa PessoaParm) {
		
		Objects.requireNonNull(PessoaParm.getId());
		
		return repository.save(PessoaParm);
	}

	@Override
	@Transactional
	public void deletar(Pessoa PessoaParm) {
		
		Objects.requireNonNull(PessoaParm.getId());
		repository.delete(PessoaParm);
				
	}

	@Override
	@Transactional
	public List<Pessoa> buscar(Pessoa PessoaParm) {
		
		Example example = Example.of(PessoaParm);
		
		return repository.findAll(example);
	}

	@Override
	@Transactional
	public Optional<Pessoa> consultarPorId(Integer idPessoa) {
				
		
		return repository.findById(idPessoa);
	}

}
