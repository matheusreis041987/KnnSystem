package com.knnsystem.api.service.impl;

import java.util.List;


import com.knnsystem.api.exceptions.EntidadeCadastradaException;
import com.knnsystem.api.exceptions.RelatorioSemResultadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.dto.ApartamentoFormularioDTO;
import com.knnsystem.api.model.repository.ApartamentoRepository;
import com.knnsystem.api.service.ApartamentoService;



@Service
public class ApartamentoServiceImpl implements ApartamentoService {

	@Autowired
	private ApartamentoRepository repository;

	@Override
	@Transactional
	public List<ApartamentoFormularioDTO> listar() {
		var apartamentos = repository
				.findAll()
				.stream()
				.map(ApartamentoFormularioDTO::new)
				.toList();

		if (apartamentos.isEmpty()) {
			throw new RelatorioSemResultadoException("Erro -  não há dados para o relatório");
		}
		return apartamentos;
	}

	@Override
	@Transactional
	public ApartamentoFormularioDTO salvar(ApartamentoFormularioDTO apartamentoFormularioDTO) {

		if (repository
				.findByNumAptAndBlocoApt(
						apartamentoFormularioDTO.numeroDoApartamento(),
						apartamentoFormularioDTO.bloco()).isPresent()){
			throw new EntidadeCadastradaException("Já há um apartamento cadastrado para os dados informados");
		}

		return null;
	}
}
