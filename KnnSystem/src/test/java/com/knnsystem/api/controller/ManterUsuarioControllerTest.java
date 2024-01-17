package com.knnsystem.api.controller;

import com.knnsystem.api.model.entity.Usuario;
import com.knnsystem.api.model.repository.PessoaRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ManterUsuarioControllerTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Usuario usuarioAtivo;

    private Usuario usuarioInativo;

    private final String ENDPOINT_CONSULTA_BASE = "/usuario/api";

    private final String ENDPOINT_CADASTRO = "/usuario/api/cadastra";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TestDataBuilder testDataBuilder;

    @BeforeEach
    void setUp(){
        usuarioAtivo = testDataBuilder.createUsuarioAtivo();
        usuarioRepository.save(usuarioAtivo);
    }

    @AfterEach
    void tearDown(){
        usuarioRepository.deleteAll();
    }

    @DisplayName("Testa que não pode cadastrar novo usuário com cpf já utilizado")
    @Test
    void naoDeveCadastrarCpfPelaSegundaVez() throws Exception {
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"cpf\": \"" + usuarioAtivo.getCpf() + "\", " +
                                                "\"nome\": \"" + usuarioAtivo.getNome() + "\", " +
                                                "\"email\": \"" + usuarioAtivo.getEmail() + "\", " +
                                                "\"dataNascimento\": \"" + usuarioAtivo.getDataNascimento() + "\", " +
                                                "\"cargo\": \"" + usuarioAtivo.getCargo() + "\", " +
                                                "\"senhaProvisoria\": \"1234567\"}"
                                )
                )
                // Assert
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("CPF já cadastrado")))
        ;
    }


    @DisplayName("Testa cadastro de usuário com dados válidos")
    @Test
    void deveCadastrarUsuarioComDadosValidos() throws Exception {

        // Arrange
        Usuario usuarioNovo = testDataBuilder.createUsuarioNovo();

        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"cpf\": \"" + usuarioNovo.getCpf() + "\", " +
                                                "\"nome\": \"" + usuarioNovo.getNome() + "\", " +
                                                "\"email\": \"" + usuarioNovo.getEmail() + "\", " +
                                                "\"dataNascimento\": \"" + usuarioNovo.getDataNascimento() + "\", " +
                                                "\"cargo\": \"" + usuarioNovo.getCargo() + "\", " +
                                                "\"senhaProvisoria\": \"12A45678\"}"
                                )
                )
                // Assert
                .andExpect(status().isCreated());
    }

    @DisplayName("Testa consulta de usuário por CPF retorna erro se não encontra")
    @Test
    void testDeveLancarErroSeNaoEncontrarUsuarioPorCPF() throws Exception {
        // Arrange
        Usuario usuarioNovo = testDataBuilder.createUsuarioNovo();

        // Act
        this.mockMvc
                .perform(
                        get(ENDPOINT_CONSULTA_BASE + "/" + usuarioNovo.getCpf())
                )
                // Assert
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Erro - não há usuário cadastrado para esse CPF")))
        ;



    }

}