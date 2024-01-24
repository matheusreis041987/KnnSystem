package com.knnsystem.api.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.knnsystem.api.dto.ContratoCadastroDTO;
import com.knnsystem.api.exceptions.EntidadeCadastradaException;
import com.knnsystem.api.exceptions.EntidadeNaoEncontradaException;
import com.knnsystem.api.exceptions.RegraNegocioException;
import com.knnsystem.api.infrastructure.api.documental.ApiDocumentoFacade;
import com.knnsystem.api.model.entity.Gestor;
import com.knnsystem.api.model.repository.FornecedorRepository;
import com.knnsystem.api.model.repository.GestorRepository;
import com.knnsystem.api.model.repository.SindicoRepository;
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

	@Autowired
	private FornecedorRepository fornecedorRepository;

	@Autowired
	private ApiDocumentoFacade apiDocumentoFacade;
	@Autowired
	private SindicoRepository sindicoRepository;
	@Autowired
	private GestorRepository gestorRepository;


	@Override
	@Transactional
	public ContratoCadastroDTO salvar(ContratoCadastroDTO dto) {
		if (contratoRepository.findByNumContrato(dto.numeroContrato()).isPresent()) {
			throw new EntidadeCadastradaException("Já há um contrato cadastrado para os dados informados");
		}

		boolean isCpfValido = apiDocumentoFacade.validarDocumento(dto.gestor().cpf());

		if (!isCpfValido) {
			throw new RegraNegocioException("CPF de gestor inválido");
		}

		var fornecedorOptional = fornecedorRepository.findByNumControle(dto.numeroControleFornecedor());
		if (fornecedorOptional.isEmpty()) {
			throw new EntidadeNaoEncontradaException("Não encontrado fornecedor para o número de controle informado");
		}

		var sindicoOptional = sindicoRepository.findByEmail(dto.emailSindico());
		if (sindicoOptional.isEmpty()) {
			throw new EntidadeNaoEncontradaException("E-mail não corresponde ao do síndico");
		}

		var contrato = dto.toModel(true, fornecedorOptional.get(), sindicoOptional.get());

		// salva entidade relacionada primeiro
		var gestorSalvo =  gestorRepository.save(dto.gestor().toModel());
		contrato.setGestor(gestorSalvo);

		// salva o contrato
		var contratoSalvo = contratoRepository.save(contrato);

		return new ContratoCadastroDTO(contratoSalvo);
	}
}
