package com.knnsystem.api.model.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RescisaoTest {

    @DisplayName("Testa cálculo de rescisão com prazo superior a um ano de antecipação")
    @Test
    void testCalculoRescisaoPrazoSuperiorAUmAno(){
        // Arrange
        Contrato contrato = new Contrato();
        contrato.setValorMensalInicial(new BigDecimal("1000"));
        contrato.setVigenciaFinal(LocalDate.of(2023, 1, 1));
        Rescisao rescisao = new Rescisao(contrato);
        rescisao.setDtRescisao(LocalDate.of(2021, 12, 31));
        rescisao.setCausador(CausadorRescisao.FORNECEDOR);

        // Act
        rescisao.calcularRescisao();

        // Assert
        assertEquals(
                rescisao.getValorRescisao(),
                new BigDecimal("3660.00")
        );
    }

    @DisplayName("Testa cálculo de rescisão com prazo de um dia de antecipação")
    @Test
    void testCalculoRescisaoPrazoDeUmDiaDeAntecipacao(){
        // Arrange
        Contrato contrato = new Contrato();
        contrato.setValorMensalInicial(new BigDecimal("1000"));
        contrato.setVigenciaFinal(LocalDate.of(2022, 1, 1));
        Rescisao rescisao = new Rescisao(contrato);
        rescisao.setDtRescisao(LocalDate.of(2021, 12, 31));
        rescisao.setCausador(CausadorRescisao.CONTRATANTE);

        // Act
        rescisao.calcularRescisao();

        // Assert
        assertEquals(
                rescisao.getValorRescisao(),
                new BigDecimal("10.00")
        );
    }

}