package com.knnsystem.api.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
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
}
