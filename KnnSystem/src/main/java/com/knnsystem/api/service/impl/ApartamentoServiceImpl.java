package com.knnsystem.api.service.impl;

import java.util.List;


import com.knnsystem.api.exceptions.EntidadeCadastradaException;
import com.knnsystem.api.exceptions.EntidadeNaoEncontradaException;
import com.knnsystem.api.exceptions.RelatorioSemResultadoException;
import com.knnsystem.api.model.entity.StatusGeral;
import com.knnsystem.api.model.repository.MoradorRepository;
import com.knnsystem.api.model.repository.ProprietarioRepository;
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

	@Autowired
	private ProprietarioRepository proprietarioRepository;

	@Autowired
	private MoradorRepository moradorRepository;

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
	public List<ApartamentoFormularioDTO> listar(Integer numero, String bloco) {
		return repository
				.findByNumAptOrBlocoApt(numero, bloco)
				.stream()
				.map(ApartamentoFormularioDTO::new)
				.toList();
	}

	@Override
	@Transactional
	public ApartamentoFormularioDTO salvar(ApartamentoFormularioDTO dto) {

		if (repository
				.findByNumAptAndBlocoApt(
						dto.moradorDTO().numeroDoApartamento(),
						dto.moradorDTO().blocoDoApartamento()).isPresent()){
			throw new EntidadeCadastradaException("Já há um apartamento cadastrado para os dados informados");
		}

		var apartamento = dto.toModel(true);

		var proprietario = proprietarioRepository.findByCpf(dto.proprietarioDTO().cpf());
		var morador = moradorRepository.findByCpf(dto.moradorDTO().cpf());

		if (proprietario.isEmpty()) {
			throw new EntidadeNaoEncontradaException("Não há cadastro para o proprietário informado");
		}

		if (morador.isEmpty()) {
			throw new EntidadeNaoEncontradaException("Não hác cadastro para o morador informado");
		}

		apartamento.setMorador(morador.get());
		apartamento.setProprietario(proprietario.get());
		var apartamentoSalvo = repository.save(apartamento);

		return new ApartamentoFormularioDTO(apartamentoSalvo);

	}

	@Override
	@Transactional
	public ApartamentoFormularioDTO inativar(Integer numero, String bloco) {
		var apartamentoAInativar = repository.findByNumAptAndBlocoApt(numero, bloco);

		if (apartamentoAInativar.isEmpty()) {
			throw new EntidadeNaoEncontradaException("Não há um apartamento cadastrado para os dados informados");
		}

		var apartamento = apartamentoAInativar.get();
		apartamento.setStatusApt(StatusGeral.INATIVO);
		repository.save(apartamento);

		return new ApartamentoFormularioDTO(apartamento);
	}

	@Override
	@Transactional
	public void excluir(Long id) {
		var entidadeAExcluir = repository.findById(id);
		if (entidadeAExcluir.isEmpty()) {
			throw new EntidadeNaoEncontradaException("Não existe o registro solicitado");
		}
		entidadeAExcluir.ifPresent(repository::delete);
	}
}
