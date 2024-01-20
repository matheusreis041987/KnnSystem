package com.knnsystem.api.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransacaoFinanceiraDTO(
        @NotNull
        LocalDate data,
        @NotNull
        String tipo,
        String descricao,
        BigDecimal valor
) {
}
