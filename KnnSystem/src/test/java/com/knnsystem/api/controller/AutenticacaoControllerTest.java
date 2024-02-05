package com.knnsystem.api.controller;

import com.knnsystem.api.model.entity.Usuario;
import com.knnsystem.api.model.repository.UsuarioRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class AutenticacaoControllerTest {


    private Usuario usuarioAtivo;

    private Usuario usuarioInativo;

    private final String ENDPOINT_LOGIN = "/auth/api/login";

    private final String ENDPOINT_REDEFINE = "/auth/api/redefine";

    private final String ENDPOINT_ESQUECI_SENHA = "/auth/api/esqueci-senha";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TestDataBuilder testDataBuilder;

    @BeforeEach
    void setUp(){

        usuarioAtivo = testDataBuilder.createUsuarioAtivo();
        usuarioRepository.save(usuarioAtivo);

        usuarioInativo = testDataBuilder.createUsuarioInativo();
        usuarioRepository.save(usuarioInativo);

    }

    @AfterEach
    void tearDown(){
    }

    @DisplayName("Testa que consegue logar com senha correta")
    @Test
    @Transactional
    void deveLogarComSenhaCorreta() throws Exception {
        // Act
        this.mockMvc.perform(
                post(ENDPOINT_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\"cpf\": \"" + usuarioAtivo.getCpf() + "\", " +
                                        "\"senha\": \"123456Ab\"}"
                        )
        )
        // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome",
                        Matchers.is(usuarioAtivo.getNome())))
                .andExpect(jsonPath("$.perfil",
                        Matchers.is("Sindico")))
        ;
    }

    @DisplayName("Testa que não consegue logar com senha incorreta")
    @Test
    @Transactional
    void naodeveLogarComSenhaIncorreta() throws Exception {
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_LOGIN)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"cpf\": \"" + usuarioAtivo.getCpf() + "\", " +
                                                "\"senha\": \"1234567xx\"}"
                                )
                )
                // Assert
                .andExpect(status().isForbidden());
    }

    @DisplayName("Testa que não consegue logar com cpf incorreto")
    @Test
    @Transactional
    void naodeveLogarComCpfIncorreto() throws Exception {
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_LOGIN)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"cpf\": \"79791638004\", " +
                                                "\"senha\": \"123456aC\"}"
                                )
                )
                // Assert
                .andExpect(status().isForbidden());
    }

    @DisplayName("Testa que consegue logar com senha provisória e atualiza")
    @Test
    @Transactional
    void deveLogarComSenhaProvisoriaEAtualizar() throws Exception {
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_REDEFINE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"cpf\": \"" + usuarioAtivo.getCpf() + "\", " +
                                                "\"senhaProvisoria\": \"123456Ab\", " +
                                                "\"novaSenha\": \"654321AB\"}"
                                )
                )
                // Assert
                .andExpect(status().isOk());
        var usuarioAtualizado = usuarioRepository.findByCpf(usuarioAtivo.getCpf());

        assertTrue(
                passwordEncoder.matches("654321AB", usuarioAtualizado.get().getSenha())
        );
    }

    @DisplayName("Testa que não consegue logar com senha provisória incorreta")
    @Test
    @Transactional
    void NaoDeveLogarComSenhaProvisoriaIncorreta() throws Exception {
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_REDEFINE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"cpf\": \"" + usuarioAtivo.getCpf() + "\", " +
                                                "\"senhaProvisoria\": \"654321\", " +
                                                "\"novaSenha\": \"123456C8\"}"
                                )
                )
                // Assert
                .andExpect(status().isForbidden());
    }

    @DisplayName("Testa que não consegue logar se usuário está inativo")
    @Test
    @Transactional
    void naoDeveLogarSeUsuarioInativo() throws Exception {
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_LOGIN)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"cpf\": \"" + usuarioInativo.getCpf() + "\", " +
                                                "\"senha\": \"1234567y\"}"
                                )
                )
                // Assert
                .andExpect(status().isForbidden());
    }

    @DisplayName("Testa que retorna OK mesmo para cpf não existente")
    @Test
    @Transactional
    void naoDeveRevelarQueCPFNaoEstaCadastrado() throws Exception {
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_ESQUECI_SENHA)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"cpf\": \"82018582046\"} "
                                )
                )
                // Assert
                .andExpect(status().isOk());
    }

}