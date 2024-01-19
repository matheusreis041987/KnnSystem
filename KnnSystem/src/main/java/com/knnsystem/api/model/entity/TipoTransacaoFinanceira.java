package com.knnsystem.api.model.entity;

import com.knnsystem.api.exceptions.ValorTransacaoFinanceiraInvalidoException;

import java.math.BigDecimal;

public enum TipoTransacaoFinanceira {
    DESPESA {
        @Override
        void valida(BigDecimal valor) {
            if (valor.signum() > 0) {
                throw new ValorTransacaoFinanceiraInvalidoException(
                        "valor para transação financeira deve ser negativo"
                );
            }
        }
    },
    RECEITA {
        @Override
        void valida(BigDecimal valor) {

        }
    };

    abstract void valida(BigDecimal valor);
}
