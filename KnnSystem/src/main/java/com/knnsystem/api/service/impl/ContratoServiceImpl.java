package com.knnsystem.api.service.impl;

import com.knnsystem.api.dto.ContratoDTO;
import com.knnsystem.api.exceptions.EntidadeCadastradaException;
import com.knnsystem.api.exceptions.EntidadeNaoEncontradaException;
import com.knnsystem.api.exceptions.RegraNegocioException;
import com.knnsystem.api.infrastructure.api.documental.ApiDocumentoFacade;
import com.knnsystem.api.model.entity.Contrato;
import com.knnsystem.api.model.entity.Fornecedor;
import com.knnsystem.api.model.repository.FornecedorRepository;
import com.knnsystem.api.model.repository.GestorRepository;
import com.knnsystem.api.model.repository.SindicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.model.repository.ContratoRepository;
import com.knnsystem.api.service.ContratoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	public ContratoDTO salvar(ContratoDTO dto) {
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

		return new ContratoDTO(contratoSalvo);
	}

	@Override
	public List<ContratoDTO> listar(String cnpjFornecedor, String razaoSocial, String numeroContrato) {
		List<Fornecedor> fornecedores = new ArrayList<>();
		if (cnpjFornecedor != null){
			var fornecedorOptional = fornecedorRepository.findByCnpj(cnpjFornecedor);
            fornecedorOptional.ifPresent(fornecedores::add);
		}
		if (razaoSocial != null) {
			var fornecedorOptional = fornecedorRepository.findByRazaoSocial(razaoSocial);
			fornecedorOptional.ifPresent(fornecedores::add);

		}
		List<Contrato> contratos = new ArrayList<>();
		for (Fornecedor fornecedor: fornecedores) {
			contratos = contratoRepository.findByFornecedor(fornecedor);
		}
		Optional<Contrato> contratoPorNumeroOptional = Optional.empty();
		if (numeroContrato != null){
			contratoPorNumeroOptional = contratoRepository.findByNumContrato(numeroContrato);
		}

		if (contratoPorNumeroOptional.isPresent()){
			contratos.add(contratoPorNumeroOptional.get());
		}

		return contratos.stream().map(ContratoDTO::new).toList();
	}
}
