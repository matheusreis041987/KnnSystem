package com.knnsystem.api.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.knnsystem.api.dto.MoradorDTO;
import com.knnsystem.api.exceptions.EntidadeCadastradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.model.entity.Morador;
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
		return null;
	}
}
