package com.knnsystem.api.infrastructure.api.financeiro.impl;


import com.knnsystem.api.dto.DadosPagamentoDTO;
import com.knnsystem.api.dto.ResultadoPagamentoDTO;
import com.knnsystem.api.exceptions.ErroComunicacaoComBancoException;
import com.knnsystem.api.infrastructure.api.financeiro.ApiInsituicaoFinanceiraService;
import com.knnsystem.api.model.entity.StatusPagamento;
import org.springframework.stereotype.Service;

@Service
public class ApiBancoDoBrasilServiceErroImpl implements ApiInsituicaoFinanceiraService {
    @Override
    public ResultadoPagamentoDTO efetuarPagamento(DadosPagamentoDTO dto) {
        throw new ErroComunicacaoComBancoException("Operação não realizada - tente mais tarde");
    }
}
