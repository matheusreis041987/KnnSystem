package com.knnsystem.api.controller;

import com.knnsystem.api.model.entity.Pessoa;
import com.knnsystem.api.model.entity.Usuario;
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

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class AutenticacaoControllerTest {

    private Usuario usuario;
    private final String ENDPOINT_LOGIN = "/auth/api/login";

    private final String ENDPOINT_REDEFINE = "/auth/api/redefine";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp(){
        // Arrange
        Pessoa pessoa = new Pessoa();
        // https://www.4devs.com.br/gerador_de_cpf
        pessoa.setCpf("56214649070");
        pessoa.setNome("Nome da Pessoa");
        pessoa.setEmail("emaildapessoa@email");

        usuario = new Usuario(pessoa);
        usuario.setCargo("Cargo");
        usuario.setPerfil("Perfil");
        usuario.setSenha(passwordEncoder.encode("123456"));
        usuario.setDataNascimento(LocalDate.of(1971,1 ,1));

        usuarioRepository.save(usuario);

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
                                "{\"cpf\": \"" + usuario.getCpf() + "\", " +
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
                                        "{\"cpf\": \"" + usuario.getCpf() + "\", " +
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
                                        "{\"cpf\": \"" + usuario.getCpf() + "\", " +
                                                "\"senhaProvisoria\": \"123456\", " +
                                                "\"novaSenha\": \"654321\"}"
                                )
                )
                // Assert
                .andExpect(status().isOk());
        var usuarioAtualizado = usuarioRepository.findByCpf(usuario.getCpf());
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
                                        "{\"cpf\": \"" + usuario.getCpf() + "\", " +
                                                "\"senhaProvisoria\": \"654321\", " +
                                                "\"novaSenha\": \"123456\"}"
                                )
                )
                // Assert
                .andExpect(status().isForbidden());
    }

}