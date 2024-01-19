package com.knnsystem.api.model.entity;

import com.knnsystem.api.exceptions.ValorTransacaoFinanceiraInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TipoTransacaoFinanceiraTest {
    private final TipoTransacaoFinanceira tipoDespesa = TipoTransacaoFinanceira.DESPESA;

    private final TipoTransacaoFinanceira tipoReceita = TipoTransacaoFinanceira.RECEITA;

    @DisplayName("testa transação do tipo despesa não pode ser positiva")
    @Test
    void testTransacaoDoTipoDespesaNaoAceitaValorPositivo(){
        // Act & Assert
        assertThrows(
            ValorTransacaoFinanceiraInvalidoException.class,
                () -> new TransacaoFinanceira(
                        tipoDespesa,
                        LocalDate.now(),
                        BigDecimal.ONE,
                        "despesa"
                )
        );
    }

    @DisplayName("testa transação do tipo receita não pode ser negativa")
    @Test
    void testTransacaoDoTipoReceitaNaoAceitaValorNegativo(){
        // Act & Assert
        assertThrows(
                ValorTransacaoFinanceiraInvalidoException.class,
                () -> new TransacaoFinanceira(
                        tipoReceita,
                        LocalDate.now(),
                        new BigDecimal("-0.01"),
                        "receita"
                )
        );
    }

    @DisplayName("testa transação do tipo despesa não pode ser nula")
    @Test
    void testTransacaoDoTipoDespesaNaoAceitaValorNulo(){
        // Act & Assert
        assertThrows(
                ValorTransacaoFinanceiraInvalidoException.class,
                () -> new TransacaoFinanceira(
                        tipoDespesa,
                        LocalDate.now(),
                        BigDecimal.ZERO,
                        "despesa"
                )
        );
    }

    @DisplayName("testa transação do tipo receita não pode ser nulo")
    @Test
    void testTransacaoDoTipoReceitaNaoAceitaValorNulo(){
        // Act & Assert
        assertThrows(
                ValorTransacaoFinanceiraInvalidoException.class,
                () -> new TransacaoFinanceira(
                        tipoReceita,
                        LocalDate.now(),
                        BigDecimal.ZERO,
                        "receita"
                )
        );
    }

}