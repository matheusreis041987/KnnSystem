package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.ExtratoFinanceiro;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record ExtratoFinanceiroDTO(
        List<TransacaoFinanceiraDTO> transacoes,

        @NotNull
        LocalDate dataEmissao,

        @NotNull
        LocalDate dataInicio,

        @NotNull
        LocalDate dataFim,

        @NotNull
        BigDecimal totalEntradas,

        @NotNull
        BigDecimal totalSaidas,

        @NotNull
        BigDecimal saldoInicio,

        @NotNull
        BigDecimal saldoFim
) {

        public ExtratoFinanceiroDTO(ExtratoFinanceiro extrato) {
                this(extrato.getTransacoes().stream().map(TransacaoFinanceiraDTO::new).toList(),
                        extrato.getDataEmissao(),
                        extrato.getDataInicio(),
                        extrato.getDataFim(),
                        extrato.getTotalEntradas(),
                        extrato.getTotalSaidas(),
                        extrato.getSaldoInicial(),
                        extrato.getSaldoFim()
                        );
        }
}
