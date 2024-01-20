package com.knnsystem.api.model.entity;

import com.knnsystem.api.exceptions.RegraNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ExtratoFinanceiroTest {

    private ExtratoFinanceiro extrato;

    @DisplayName("Testa extrato não pode ter data inicial superior a data final")
    @Test
    void testDataInicialDeveSerIgualOuMenorQueDataFinal(){
        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> new ExtratoFinanceiro(
                        LocalDate.now().plusDays(1),
                        LocalDate.now(),
                        BigDecimal.ONE
                )
        );
    }

    @DisplayName("Testa extrato não pode ter data inicial nula")
    @Test
    void testDataInicialNaoPodeSerNula(){
        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> new ExtratoFinanceiro(
                        null,
                        LocalDate.now(),
                        BigDecimal.ONE
                )
        );
    }

    @DisplayName("Testa extrato não pode ter data fim nula")
    @Test
    void testDataFimNaoPodeSerNula(){
        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> new ExtratoFinanceiro(
                        LocalDate.now(),
                        null,
                        BigDecimal.ONE
                )
        );
    }

    @DisplayName("Testa extrato não pode ter saldo inicial nulo")
    @Test
    void testSaldoInicialNaoPodeSerNulo(){
        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> new ExtratoFinanceiro(
                        LocalDate.now(),
                        LocalDate.now().plusDays(1),
                        null
                )
        );
    }

    @DisplayName("Testa extrato financeiro atualiza total de entradas após adicao de receita")
    @Test
    void testAtualizaTotalDeEntradasAposAdicaoDeReceita(){
        // Arrange
        ExtratoFinanceiro extrato = new ExtratoFinanceiro(
                LocalDate.now().plusDays(-1),
                LocalDate.now(),
                BigDecimal.ZERO
        );
        TransacaoFinanceira transacao = new TransacaoFinanceira(
                TipoTransacaoFinanceira.RECEITA,
                LocalDate.now().plusDays(-1),
                BigDecimal.ONE,
                "Receita de aluguéis"
        );

        // Act
        extrato.adiciona(transacao);

        // Assert
        assertEquals(transacao.getValor(), extrato.getTotalEntradas());
    }

    @DisplayName("Testa extrato financeiro atualiza saldo Fim após adicao de receita")
    @Test
    void testAtualizaSaldoFimAposAdicaoDeReceita(){
        // Arrange
        ExtratoFinanceiro extrato = new ExtratoFinanceiro(
                LocalDate.now().plusDays(-1),
                LocalDate.now(),
                BigDecimal.ZERO
        );
        TransacaoFinanceira transacao = new TransacaoFinanceira(
                TipoTransacaoFinanceira.RECEITA,
                LocalDate.now().plusDays(-1),
                BigDecimal.ONE,
                "Receita de aluguéis"
        );

        // Act
        extrato.adiciona(transacao);

        // Assert
        assertEquals(transacao.getValor(), extrato.getSaldoFim());
    }
}