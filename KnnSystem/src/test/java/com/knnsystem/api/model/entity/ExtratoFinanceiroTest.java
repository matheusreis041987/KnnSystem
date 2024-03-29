package com.knnsystem.api.model.entity;

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

    @DisplayName("Testa extrato financeiro atualiza total de saídas após adicao de despesa")
    @Test
    void testAtualizaTotalDeSaidasAposAdicaoDeDespesa(){
        // Arrange
        ExtratoFinanceiro extrato = new ExtratoFinanceiro(
                LocalDate.now().plusDays(-1),
                LocalDate.now(),
                BigDecimal.ZERO
        );
        TransacaoFinanceira transacao = new TransacaoFinanceira(
                TipoTransacaoFinanceira.DESPESA,
                LocalDate.now(),
                BigDecimal.ONE.negate(),
                "Despesa com funcionários"
        );

        // Act
        extrato.adiciona(transacao);

        // Assert
        assertEquals(transacao.getValor(), extrato.getTotalSaidas());
    }

    @DisplayName("Testa extrato financeiro lança erro ao adicionar transação anterior ao período")
    @Test
    void testNaoDeveReceberTransacaoAnteriorADataInicio(){
        // Arrange
        ExtratoFinanceiro extrato = new ExtratoFinanceiro(
                LocalDate.now().plusDays(-1),
                LocalDate.now(),
                BigDecimal.ZERO
        );
        TransacaoFinanceira transacao = new TransacaoFinanceira(
                TipoTransacaoFinanceira.RECEITA,
                LocalDate.now().plusDays(-2),
                BigDecimal.ONE,
                "Receita de aluguéis"
        );

        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> extrato.adiciona(transacao)
        );

    }

    @DisplayName("Testa extrato financeiro lança erro ao adicionar transação posterior ao período")
    @Test
    void testNaoDeveReceberTransacaoPosteriorADataFim(){
        // Arrange
        ExtratoFinanceiro extrato = new ExtratoFinanceiro(
                LocalDate.now().plusDays(-1),
                LocalDate.now(),
                BigDecimal.ZERO
        );
        TransacaoFinanceira transacao = new TransacaoFinanceira(
                TipoTransacaoFinanceira.DESPESA,
                LocalDate.now().plusDays(1),
                BigDecimal.ONE.negate(),
                "Despesa de manutenção"
        );

        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> extrato.adiciona(transacao)
        );

    }
}