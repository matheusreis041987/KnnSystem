package com.knnsystem.api.model.entity;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ExtratoFinanceiro {
    private LocalDate dataInicio;

    private LocalDate dataFim;

    private Set<TransacaoFinanceira> transacoes;

    private BigDecimal saldoInicial;

    public ExtratoFinanceiro(
            @NotNull LocalDate dataInicio,
            @NotNull LocalDate dataFim,
            @NotNull BigDecimal saldoInicial
    ){
        // Validações prévias à instanciação
        if (dataInicio == null
                || dataFim == null
                || saldoInicial == null){
            throw new IllegalArgumentException("argumentos não podem ser nulos");
        }

        if (dataFim.isBefore(dataInicio)) {
            throw new IllegalArgumentException("data fim antes de data início");
        }

        // passou pelas validações, pode instanciar novo objeto
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.saldoInicial = saldoInicial;
        this.transacoes = new HashSet<>();
    }

}
