package com.knnsystem.api.model.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExtratoFinanceiro {

    @Getter
    private final LocalDate dataEmissao;

    @Getter
    private final LocalDate dataInicio;

    @Getter
    private final LocalDate dataFim;

    private final List<TransacaoFinanceira> transacoes;

    @Getter
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
        this.transacoes = new ArrayList<>();
        this.totalEntradas = BigDecimal.ZERO;
        this.totalSaidas = BigDecimal.ZERO;
    }

    public void adiciona(TransacaoFinanceira transacao) {
        // valida transação está entre data início e data fim do extrado
        if (transacao.getData().isBefore(dataInicio)
                || transacao.getData().isAfter(dataFim)) {
            throw new IllegalArgumentException("Transação deve estar no período do extrato");
        }

        // prossegue com atualização do extrato
        if (transacao.getTipo().equals(TipoTransacaoFinanceira.RECEITA)){
            this.totalEntradas = this.totalEntradas.add(transacao.getValor());
        } else {
            this.totalSaidas = this.totalSaidas.add(transacao.getValor());
        }
        this.saldoFim = this.saldoFim.add(transacao.getValor());
        this.transacoes.add(transacao);
    }


    public List<TransacaoFinanceira> getTransacoes() {
        return Collections.unmodifiableList(transacoes);
    }
}
