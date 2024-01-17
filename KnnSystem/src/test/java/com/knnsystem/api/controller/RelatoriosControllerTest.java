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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class RelatoriosControllerTest {

    private Usuario usuarioSindico;

    private final String ENDPOINT_RELATORIO_APARTAMENTOS = "/relatorio/api/apartamentos";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataBuilder testDataBuilder;

    @BeforeEach
    void setUp(){
        usuarioSindico = testDataBuilder.createUsuarioAdministrador();
    }

    @DisplayName("testa relatório de apartamentos sem resultados retorna erro")
    @Test
    void deveLancarErroRelatorioDeApartamentosSeNaoHouverResultados() throws Exception {
        // Act
        this.mockMvc
                .perform(
                        get(ENDPOINT_RELATORIO_APARTAMENTOS)
                                .with(user(usuarioSindico))
                )
                // Assert
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Erro -  não há dados para o relatório")))
        ;
    }

}