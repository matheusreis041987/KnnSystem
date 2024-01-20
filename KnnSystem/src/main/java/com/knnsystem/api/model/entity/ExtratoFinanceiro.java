package com.knnsystem.api.model.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ExtratoFinanceiro {

    private LocalDate dataEmissao;


    private LocalDate dataInicio;

    private LocalDate dataFim;

    private Set<TransacaoFinanceira> transacoes;

    private BigDecimal saldoInicial;

    @Getter
    private BigDecimal saldoFim;

    @Getter
    private BigDecimal totalEntradas;

    @Getter
    private BigDecimal totalSaidas;
    
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
        this.dataEmissao = LocalDate.now();
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.saldoInicial = saldoInicial;
        this.saldoFim = this.saldoInicial;
        this.transacoes = new HashSet<>();
        this.totalEntradas = BigDecimal.ZERO;
        this.totalSaidas = BigDecimal.ZERO;
    }

    public void adiciona(TransacaoFinanceira transacao) {
        if (transacao.getTipo().equals(TipoTransacaoFinanceira.RECEITA)){
            this.totalEntradas = this.totalEntradas.add(transacao.getValor());
        } else {
            this.totalSaidas = this.totalSaidas.add(transacao.getValor());
        }
        this.saldoFim = this.saldoFim.add(transacao.getValor());
    }
}
