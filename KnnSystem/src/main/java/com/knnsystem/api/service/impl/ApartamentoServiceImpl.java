package com.knnsystem.api.service.impl;

import java.util.List;


import com.knnsystem.api.exceptions.EntidadeCadastradaException;
import com.knnsystem.api.exceptions.EntidadeNaoEncontradaException;
import com.knnsystem.api.exceptions.RelatorioSemResultadoException;
import com.knnsystem.api.model.entity.Morador;
import com.knnsystem.api.model.entity.Proprietario;
import com.knnsystem.api.model.entity.StatusGeral;
import com.knnsystem.api.model.repository.MoradorRepository;
import com.knnsystem.api.model.repository.ProprietarioRepository;
import com.knnsystem.api.model.repository.TelefoneRepository;
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

	@Autowired
	private TelefoneRepository telefoneRepository;

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
		if (numero == null && bloco == null) {
			return repository.findAll().stream().map(ApartamentoFormularioDTO::new).toList();
		}
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

		Proprietario proprietarioModel;
		Morador moradorModel;

		if (proprietario.isEmpty()) {
			proprietarioModel = dto.proprietarioDTO().toModel(true);
			proprietarioRepository.save(proprietarioModel);
			var telefone = proprietarioModel.getTelefonePrincipal();
			telefone.setPessoa(proprietarioModel);
			telefoneRepository.save(telefone);
		} else {
			proprietarioModel = proprietario.get();
		}

		if (morador.isEmpty()) {
			moradorModel = dto.moradorDTO().toModel(true);
			moradorRepository.save(moradorModel);
			var telefone = moradorModel.getTelefonePrincipal();
			telefone.setPessoa(moradorModel);
			telefoneRepository.save(telefone);
		} else {
			moradorModel = morador.get();
		}

		apartamento.setMorador(moradorModel);
		apartamento.setProprietario(proprietarioModel);
		var apartamentoSalvo = repository.save(apartamento);

		return new ApartamentoFormularioDTO(apartamentoSalvo);

	}

	@Override
	@Transactional
	public void inativar(Long id) {
		var apartamento = repository.findById(id);
		apartamento
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Não há um apartamento cadastrado para os dados informados"))
				.setStatusApt(StatusGeral.INATIVO);
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
