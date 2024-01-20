package com.knnsystem.api.controller;


import com.knnsystem.api.model.entity.Usuario;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ExtratoFinanceiroControllerTest {

    private Usuario usuarioSecretaria;

    private final String ENDPOINT_CONSULTA = "/extrato/api/consulta";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataBuilder testDataBuilder;

    @BeforeEach
    void setUp(){
        usuarioSecretaria = testDataBuilder.createUsuarioSecretaria();
    }

    @DisplayName("Testa obtem extrato com totais corretos para pedido de um mês")
    @Test
    void testObtemExtratoComTotaisCorretosParaPedidoDeUmMes() throws Exception {
        // Act
        this.mockMvc.perform(
                        get(ENDPOINT_CONSULTA)
                                .param("anoInicio", "2023")
                                .param("mesInicio", "12")
                                .param("anoFim", "2023")
                                .param("mesFim", "12")
                                .with(user(usuarioSecretaria)))
            // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transacoes",
                        Matchers.hasSize(8)))
                .andExpect(jsonPath("$.dataEmissao",
                        Matchers.is(LocalDate.now().toString())))
                .andExpect(jsonPath("$.dataInicio",
                        Matchers.is("2023-12-01")))
                .andExpect(jsonPath("$.dataFim",
                        Matchers.is("2023-12-31")))
                .andExpect(jsonPath("$.totalEntradas",
                        Matchers.is(3575)))
                .andExpect(jsonPath("$.totalSaidas",
                        Matchers.is(-4150)))
                .andExpect(jsonPath("$.saldoInicio",
                        Matchers.is(829.26)))
                .andExpect(jsonPath("$.saldoFim",
                        Matchers.is(254.26)))
        ;
    }

    @DisplayName("Testa erro do extrato ao não encontrar transações")
    @Test
    void testErroSeConsultaSemResultado() throws Exception {
        // Act
        this.mockMvc.perform(
                        get(ENDPOINT_CONSULTA)
                                .param("anoInicio", "2023")
                                .param("mesInicio", "10")
                                .param("anoFim", "2023")
                                .param("mesFim", "11")
                                .with(user(usuarioSecretaria)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("não há valores para o período")));
    }

}