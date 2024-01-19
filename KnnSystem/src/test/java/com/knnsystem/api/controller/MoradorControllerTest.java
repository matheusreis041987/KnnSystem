package com.knnsystem.api.controller;

import com.knnsystem.api.model.entity.Morador;
import com.knnsystem.api.model.entity.Usuario;
import com.knnsystem.api.model.repository.MoradorRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class MoradorControllerTest {

    private Morador moradorA;

    private Morador moradorB;

    private Usuario usuarioSecretaria;

    private final String ENDPOINT_CADASTRO = "/morador/api/cadastra";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MoradorRepository moradorRepository;

    @Autowired
    private TestDataBuilder testDataBuilder;

    @BeforeEach
    void setUp(){
        usuarioSecretaria = testDataBuilder.createUsuarioSecretaria();
        moradorA = testDataBuilder.getMoradorA();
        moradorB = testDataBuilder.getMoradorB();
    }

    @AfterEach
    void tearDown(){

    }

    @DisplayName("Testa que não pode cadastrar morador mais de uma vez")
    @Test
    @Transactional
    void naoDeveCadastrarMoradorPelaSegundaVez() throws Exception {
        // Arrange
        moradorA = moradorRepository.save(moradorA);
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"nome\": \"" + moradorA.getNome() + "\", " +
                                                "\"cpf\": \"" + moradorA.getCpf() + "\", " +
                                                "\"email\": \"" + moradorA.getEmail() + "\", " +
                                                "\"telefone\": \"" + moradorA.getTelefones().stream().findFirst() + "\", " +
                                                "\"numeroDoApartamento\": " + moradorA.getNumApt() + ", " +
                                                "\"blocoDoApartamento\": \"" + moradorA.getBloco() + "\"}"
                                )
                )
                // Assert
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Já há um morador cadastrado para os dados informados")))
        ;
    }

}