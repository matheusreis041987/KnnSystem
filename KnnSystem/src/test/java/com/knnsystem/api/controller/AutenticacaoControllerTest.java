package com.knnsystem.api.controller;

import com.knnsystem.api.model.entity.Usuario;
import com.knnsystem.api.model.repository.PessoaRepository;
import com.knnsystem.api.model.repository.UsuarioRepository;
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


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

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
        usuarioRepository.deleteAll();
    }

    @DisplayName("Testa que consegue logar com senha correta")
    @Test
    void deveLogarComSenhaCorreta() throws Exception {
        // Act
        this.mockMvc.perform(
                post(ENDPOINT_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\"cpf\": \"" + usuarioAtivo.getCpf() + "\", " +
                                        "\"senha\": \"123456\"}"
                        )
        )
        // Assert
                .andExpect(status().isOk());
    }

    @DisplayName("Testa que não consegue logar com senha incorreta")
    @Test
    void naodeveLogarComSenhaIncorreta() throws Exception {
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_LOGIN)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"cpf\": \"" + usuarioAtivo.getCpf() + "\", " +
                                                "\"senha\": \"1234567\"}"
                                )
                )
                // Assert
                .andExpect(status().isForbidden());
    }

    @DisplayName("Testa que não consegue logar com cpf incorreto")
    @Test
    void naodeveLogarComCpfIncorreto() throws Exception {
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_LOGIN)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"cpf\": \"56214649170\", " +
                                                "\"senha\": \"123456\"}"
                                )
                )
                // Assert
                .andExpect(status().isForbidden());
    }

    @DisplayName("Testa que consegue logar com senha provisória e atualiza")
    @Test
    void deveLogarComSenhaProvisoriaEAtualizar() throws Exception {
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_REDEFINE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"cpf\": \"" + usuarioAtivo.getCpf() + "\", " +
                                                "\"senhaProvisoria\": \"123456\", " +
                                                "\"novaSenha\": \"654321\"}"
                                )
                )
                // Assert
                .andExpect(status().isOk());
        var usuarioAtualizado = usuarioRepository.findByCpf(usuarioAtivo.getCpf());

        assertTrue(
                passwordEncoder.matches("654321", usuarioAtualizado.get().getSenha())
        );
    }

    @DisplayName("Testa que não consegue logar com senha provisória incorreta")
    @Test
    void NaoDeveLogarComSenhaProvisoriaIncorreta() throws Exception {
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_REDEFINE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"cpf\": \"" + usuarioAtivo.getCpf() + "\", " +
                                                "\"senhaProvisoria\": \"654321\", " +
                                                "\"novaSenha\": \"123456\"}"
                                )
                )
                // Assert
                .andExpect(status().isForbidden());
    }

    @DisplayName("Testa que não consegue logar se usuário está inativo")
    @Test
    void naoDeveLogarSeUsuarioInativo() throws Exception {
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_LOGIN)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"cpf\": \"" + usuarioInativo.getCpf() + "\", " +
                                                "\"senha\": \"1234567\"}"
                                )
                )
                // Assert
                .andExpect(status().isForbidden());
    }
}