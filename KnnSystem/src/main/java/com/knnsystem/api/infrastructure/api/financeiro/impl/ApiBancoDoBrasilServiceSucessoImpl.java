package com.knnsystem.api.infrastructure.api.financeiro.impl;

import com.knnsystem.api.dto.DadosPagamentoDTO;
import com.knnsystem.api.dto.ResultadoPagamentoDTO;
import com.knnsystem.api.infrastructure.api.financeiro.ApiInsituicaoFinanceiraService;
import com.knnsystem.api.model.entity.StatusPagamento;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ApiBancoDoBrasilServiceSucessoImpl implements ApiInsituicaoFinanceiraService {
    @Override
    public ResultadoPagamentoDTO efetuarPagamento(DadosPagamentoDTO dto) {
        return new ResultadoPagamentoDTO(StatusPagamento.ENVIADO_PARA_PAGAMENTO);
    }
}
