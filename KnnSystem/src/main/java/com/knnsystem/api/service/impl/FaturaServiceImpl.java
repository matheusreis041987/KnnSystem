package com.knnsystem.api.service.impl;


import com.knnsystem.api.dto.FaturaCadastroDTO;
import com.knnsystem.api.dto.ResultadoPagamentoDTO;
import com.knnsystem.api.infrastructure.api.financeiro.ApiInsituicaoFinanceiraService;
import com.knnsystem.api.model.entity.StatusPagamento;
import com.knnsystem.api.model.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.knnsystem.api.model.repository.FaturaRepository;
import com.knnsystem.api.service.FaturaService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class FaturaServiceImpl implements FaturaService  {

	private final BigDecimal LIMIAR_VALOR_APROVACAO_SINDICO = new BigDecimal("30000.00");

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
		//
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
}
