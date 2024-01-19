package com.knnsystem.api.service.impl;

import com.knnsystem.api.dto.MoradorDTO;
import com.knnsystem.api.exceptions.EntidadeCadastradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.knnsystem.api.model.repository.MoradorRepository;
import com.knnsystem.api.service.MoradorService;

@Service
public class MoradorServiceImpl implements MoradorService {
	@Autowired
	private MoradorRepository repository;
	@Override
	public MoradorDTO salvar(MoradorDTO moradorDTO) {
		if (repository.findByCpf(moradorDTO.cpf()).isPresent()) {
			throw new EntidadeCadastradaException("Já há um morador cadastrado para os dados informados");
		}

		var morador = moradorDTO.toModel(true);

		var moradorSalvo = repository.save(morador);

		return new MoradorDTO(moradorSalvo);

	}
}
