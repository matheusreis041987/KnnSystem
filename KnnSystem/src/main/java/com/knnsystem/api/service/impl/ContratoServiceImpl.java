package com.knnsystem.api.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.knnsystem.api.dto.ContratoCadastroDTO;
import com.knnsystem.api.exceptions.EntidadeCadastradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.model.entity.Contrato;
import com.knnsystem.api.model.repository.ContratoRepository;
import com.knnsystem.api.service.ContratoService;

@Service
public class ContratoServiceImpl implements ContratoService {

	@Autowired
	private ContratoRepository contratoRepository;


	@Override
	@Transactional
	public ContratoCadastroDTO salvar(ContratoCadastroDTO dto) {
		if (contratoRepository.findByNumContrato(dto.numeroContrato()).isPresent()) {
			throw new EntidadeCadastradaException("Já há um contrato cadastrado para os dados informados");
		}
		return null;
	}
}
