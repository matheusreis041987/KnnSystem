package com.knnsystem.api.infrastructure.api.financeiro;

import com.knnsystem.api.dto.DadosPagamentoDTO;
import com.knnsystem.api.dto.ResultadoPagamentoDTO;

import java.util.List;

public interface ApiInsituicaoFinanceiraService {
    ResultadoPagamentoDTO efetuarPagamento(DadosPagamentoDTO dto);
}
