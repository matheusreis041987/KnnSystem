package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.TransacaoFinanceira;
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
        public TransacaoFinanceiraDTO(TransacaoFinanceira transacaoFinanceira){
                this(transacaoFinanceira.getData(), transacaoFinanceira.getTipo().toString(),
                        transacaoFinanceira.getDescricao(), transacaoFinanceira.getValor());
        }
}
