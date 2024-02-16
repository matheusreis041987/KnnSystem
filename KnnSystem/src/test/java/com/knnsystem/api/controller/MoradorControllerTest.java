package com.knnsystem.api.controller;

import com.knnsystem.api.model.entity.Morador;
import com.knnsystem.api.model.entity.Usuario;
import com.knnsystem.api.model.repository.MoradorRepository;
import com.knnsystem.api.model.repository.TelefoneRepository;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private final String ENDPOINT_CONSULTA = "/morador/api/consulta";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MoradorRepository moradorRepository;

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Autowired
    private TestDataBuilder testDataBuilder;

    @BeforeEach
    void setUp(){
        usuarioSecretaria = testDataBuilder.createUsuarioSecretaria();
        moradorA = testDataBuilder.createMoradorA();
        moradorB = testDataBuilder.createMoradorB();
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

    @DisplayName("Testa que não pode cadastrar novo morador sem preencher o formulario completo")
    @Test
    @Transactional
    void naoDeveCadastrarMoradorComDadosIncompletos() throws Exception {
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"nome\": \"" + moradorA.getNome() + "\", " +
                                                "\"cpf\": \"" + moradorA.getCpf() + "\", " +
                                                "\"telefone\": \"" + moradorA.getTelefones().stream().findFirst() + "\", " +
                                                "\"numeroDoApartamento\": " + moradorA.getNumApt() + ", " +
                                                "\"blocoDoApartamento\": \"" + moradorA.getBloco() + "\"}"
                                )
                )
                // Assert
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("e-mail do morador é obrigatório")))
        ;
    }

    @DisplayName("Testa que não pode cadastrar novo morador com algum dado inválido")
    @Test
    @Transactional
    void naoDeveCadastrarMoradorComDadosInvalidos() throws Exception {
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"nome\": \"" + moradorA.getNome() + "\", " +
                                                "\"cpf\": \"" + moradorA.getCpf() + "\", " +
                                                "\"email\": \" abc \", " +
                                                "\"telefone\": \"" + moradorA.getTelefones().stream().findFirst() + "\", " +
                                                "\"numeroDoApartamento\": " + moradorA.getNumApt() + ", " +
                                                "\"blocoDoApartamento\": \"" + moradorA.getBloco() + "\"}"
                                )
                )
                // Assert
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("e-mail inválido")))
        ;
    }

    @DisplayName("Testa cadastro de morador com dados válidos")
    @Test
    @Transactional
    void deveCadastrarMoradorComDadosValidos() throws Exception {
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"nome\": \"" + moradorA.getNome() + "\", " +
                                                "\"cpf\": \"" + moradorA.getCpf() + "\", " +
                                                "\"email\": \"" + moradorA.getEmail() + "\", " +
                                                "\"telefone\": \"" + moradorA.getTelefonePrincipal() + "\", " +
                                                "\"numeroDoApartamento\": " + moradorA.getNumApt() + ", " +
                                                "\"blocoDoApartamento\": \"" + moradorA.getBloco() + "\"}"
                                )
                )
                // Assert
                .andExpect(status().isCreated())
        ;
    }

    @DisplayName("Testa consulta para repositório de moradores vazio")
    @Test
    @Transactional
    void deveRetornarErroSeNaoHouverMoradores() throws Exception {
        // Act
        this.mockMvc.perform(
                        get(ENDPOINT_CONSULTA)
                                .with(user(usuarioSecretaria)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Erro - Morador não cadastrado")));

    }

    @DisplayName("Testa consulta quando não encontra cpf do morador")
    @Test
    @Transactional
    void deveRetornarErroSeNaoHouverMoradorParaCpfInformado() throws Exception {
        // Arrange
        moradorRepository.save(moradorA);

        // Act
        this.mockMvc.perform(
                        get(ENDPOINT_CONSULTA)
                                .param("cpf", moradorB.getCpf())
                                .with(user(usuarioSecretaria)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Erro - Morador não cadastrado")));

    }

    @DisplayName("Testa consulta quando não encontra nome do morador")
    @Test
    @Transactional
    void deveRetornarErroSeNaoHouverMoradorParaNomeInformado() throws Exception {
        // Arrange
        moradorRepository.save(moradorB);

        // Act
        this.mockMvc.perform(
                        get(ENDPOINT_CONSULTA)
                                .param("nome", moradorA.getNome())
                                .with(user(usuarioSecretaria)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Erro - Morador não cadastrado")));

    }

    @DisplayName("Testa consulta quando encontra tanto cpf quanto nome")
    @Test
    @Transactional
    void deveRetornarMoradoresQuePossuamCpfOuNome() throws Exception {
        // Arrange
        moradorRepository.save(moradorA);
        moradorRepository.save(moradorB);

        // Act
        this.mockMvc.perform(
                        get(ENDPOINT_CONSULTA)
                                .param("cpf", moradorB.getCpf())
                                .param("nome", moradorA.getNome())
                                .with(user(usuarioSecretaria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",
                        Matchers.hasSize(2)));

    }

}