package com.knnsystem.api.model.entity;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class TransacaoFinanceira {

    private final LocalDate data;

    private final TipoTransacaoFinanceira tipo;

    private final String descricao;

    private final BigDecimal valor;

    public TransacaoFinanceira(
            TipoTransacaoFinanceira tipo,
            LocalDate data,
            BigDecimal valor,
            String descricao) {

        // Valida de imediato se valor e tipo de transação são compatíveis
        tipo.valida(valor);

        // Instancia objeto se valor e tipo forem compatíveis
        this.data = data;
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
    }

}
