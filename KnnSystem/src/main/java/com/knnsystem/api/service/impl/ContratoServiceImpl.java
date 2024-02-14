package com.knnsystem.api.service.impl;

import com.knnsystem.api.dto.ContratoDTO;
import com.knnsystem.api.dto.ReajusteParametrosDTO;
import com.knnsystem.api.dto.RescisaoCadastroDTO;
import com.knnsystem.api.exceptions.EntidadeNaoEncontradaException;
import com.knnsystem.api.exceptions.RegraNegocioException;
import com.knnsystem.api.exceptions.RelatorioSemResultadoException;
import com.knnsystem.api.infrastructure.api.documental.ApiDocumentoFacade;
import com.knnsystem.api.model.entity.*;
import com.knnsystem.api.model.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.service.ContratoService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContratoServiceImpl implements ContratoService {
	@Autowired
	private RescisaoRepository rescisaoRepository;

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
	@Transactional
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
			contratos = contratoRepository.findAllByFornecedor(fornecedor);
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

	@Override
	@Transactional
	public void inativar(Long id) {
		var contrato = obtemContratoPorId(id);
		contrato.inativar();
	}

	@Override
	@Transactional
	public void reajustar(Long id, @Valid ReajusteParametrosDTO dto) {
		var contrato = obtemContratoPorId(id);
		contrato.reajustar(dto.ipcaAcumulado(), dto.data());
	}

	@Override
	public void rescindir(Long id, RescisaoCadastroDTO dto) {
		var contrato = obtemContratoPorId(id);

		CausadorRescisao causador = dto.criaCausador();
		Rescisao rescisao = new Rescisao(contrato);
		rescisao.setDtRescisao(dto.dataRescisao());
		rescisao.setDtPgto(dto.dataRescisao());
		rescisao.setCausador(causador);

		rescisao.calcularRescisao();

		rescisaoRepository.save(rescisao);

	}

	@Override
	@Transactional
	public void excluir(Long id) {
		var entidadeAExcluir = contratoRepository.findById(id);
		if (entidadeAExcluir.isEmpty()) {
			throw new EntidadeNaoEncontradaException("Não existe o registro solicitado");
		}
		entidadeAExcluir.ifPresent(contratoRepository::delete);
	}

	@Override
	@Transactional
	public List<ContratoDTO> listarVigentes() {
		return listarContratosPorStatus(StatusContrato.ATIVO);
	}

	@Override
	@Transactional
	public List<ContratoDTO> listarVencidos() {
		return listarContratosPorStatus(StatusContrato.VENCIDO);
	}

	private List<ContratoDTO> listarContratosPorStatus(StatusContrato statusContrato) {
		var contratos = contratoRepository
				.findAllByStatusContrato(statusContrato)
				.stream()
				.map(ContratoDTO::new)
				.toList();

		if (contratos.isEmpty()) {
			throw new RelatorioSemResultadoException("Erro -  não há dados para o relatório");
		}
		return contratos;
	}

	private Contrato obtemContratoPorId(Long id){
		Optional<Contrato> contratoOptional = contratoRepository.findById(id);
		if (contratoOptional.isEmpty()) {
			throw new EntidadeNaoEncontradaException("Não há um contrato cadastrado para os dados informados");
		}
		return contratoOptional.get();
	}


}
