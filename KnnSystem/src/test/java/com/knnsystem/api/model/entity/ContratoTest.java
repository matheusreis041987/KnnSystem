package com.knnsystem.api.model.entity;

import com.knnsystem.api.exceptions.RegraNegocioException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ContratoTest {

    private Contrato contrato;

    @Test
    @DisplayName("Deve lançar exceção de regra de negócio se reajusta antes de um ano")
    void testCenarioReajusteAntesDeUmAno(){
        // Arrange
        contrato = new Contrato();
        contrato.setVigenciaInicial(LocalDate.of(2022, 8, 2));
        LocalDate dataReajuste = LocalDate.of(2023, 8, 1);
        BigDecimal ipcaAcumulado = new BigDecimal("4.98");

        // Act & Assert
        assertThrows(
                RegraNegocioException.class,
                () -> contrato.reajustar(ipcaAcumulado, dataReajuste)
        );

    }

    @Test
    @DisplayName("Deve calcular primeiro reajuste após um ano da vigência inicial")
    void testCenarioPrimeiroReajusteAposUmAno(){
        // Arrange
        contrato = new Contrato();
        contrato.setValorMensalInicial(new BigDecimal("10000"));
        contrato.setVigenciaInicial(LocalDate.of(2022, 8, 2));
        LocalDate dataReajuste = LocalDate.of(2023, 8, 3);
        BigDecimal ipcaAcumulado = new BigDecimal("4.98");

        // Act
        contrato.reajustar(ipcaAcumulado, dataReajuste);

        // Assert
        assertEquals(contrato.getValorMensalAtual(), new BigDecimal("10498.00"));



    }

}