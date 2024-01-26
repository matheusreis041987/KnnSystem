package com.knnsystem.api.service.impl;


import com.knnsystem.api.dto.FaturaCadastroDTO;
import com.knnsystem.api.dto.ResultadoPagamentoDTO;
import com.knnsystem.api.infrastructure.api.financeiro.ApiInsituicaoFinanceiraService;
import com.knnsystem.api.model.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.knnsystem.api.model.repository.FaturaRepository;
import com.knnsystem.api.service.FaturaService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FaturaServiceImpl implements FaturaService  {


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
		return apiInsituicaoFinanceiraService
				.efetuarPagamento(dadosParaInstituicaoFinanceira);
	}
}
