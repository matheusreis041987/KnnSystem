package com.knnsystem.api.service.impl;


import com.knnsystem.api.dto.FaturaCadastroDTO;
import com.knnsystem.api.dto.ResultadoPagamentoDTO;
import com.knnsystem.api.exceptions.EntidadeNaoEncontradaException;
import com.knnsystem.api.exceptions.RegraNegocioException;
import com.knnsystem.api.infrastructure.api.financeiro.ApiInsituicaoFinanceiraService;
import com.knnsystem.api.model.entity.StatusPagamento;
import com.knnsystem.api.model.repository.ContratoRepository;
import com.knnsystem.api.model.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.knnsystem.api.model.repository.FaturaRepository;
import com.knnsystem.api.service.FaturaService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class FaturaServiceImpl implements FaturaService  {

	private final BigDecimal LIMIAR_VALOR_APROVACAO_SINDICO = new BigDecimal("30000.00");

	private final BigDecimal LIMIAR_VALOR_JANELA_PAGAMENTO = new BigDecimal("10000.00");

	@Autowired
	private ContratoRepository contratoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private FaturaRepository faturaRepository;

	@Autowired
	private ApiInsituicaoFinanceiraService apiInsituicaoFinanceiraService;

	@Override
	@Transactional
	public ResultadoPagamentoDTO salvar(FaturaCadastroDTO dto) {
		var dadosParaInstituicaoFinanceira = dto.getDadosPagamentos();

		// validação a partir de 10 mil, entre 10 e 30 dias
		if (!isNaJanelaDePagamento(dto)){
			throw new RegraNegocioException("Erro - Pagamentos acima de R$ 10 mil devem ter vencimento entre 10 e 30 dias corridos da data atual");
		}
		var contratoOptional = contratoRepository.findByNumContrato(dto.numeroContrato());
		if (contratoOptional.isEmpty()){
			throw new EntidadeNaoEncontradaException("Não há contrato para o número informado");
		}

		// validação a partir de 30 mil, entre 10 e 30 dias
		if (isNecessariaAprovacaoSindico(dto)){
			return new ResultadoPagamentoDTO(
					StatusPagamento.AGUARDANDO_APROVACAO
			);
		}
		return apiInsituicaoFinanceiraService
				.efetuarPagamento(dadosParaInstituicaoFinanceira);
	}

	private boolean isNecessariaAprovacaoSindico(FaturaCadastroDTO dto){
		return dto.valor().compareTo(LIMIAR_VALOR_APROVACAO_SINDICO) > 0;
	}

	private boolean isNaJanelaDePagamento(FaturaCadastroDTO dto) {
		if (dto.valor().compareTo(LIMIAR_VALOR_JANELA_PAGAMENTO) <= 0) {
			return true;
		} else {
			return DAYS.between(LocalDate.now(), dto.dataPagamento()) >= 10 &&
					DAYS.between(LocalDate.now(), dto.dataPagamento()) <= 30;
		}
	}
}
