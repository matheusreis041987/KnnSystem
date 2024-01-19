package com.knnsystem.api.controller;

import com.knnsystem.api.model.entity.Apartamento;
import com.knnsystem.api.model.entity.Usuario;
import com.knnsystem.api.model.repository.ApartamentoRepository;
import com.knnsystem.api.model.repository.MoradorRepository;
import com.knnsystem.api.model.repository.ProprietarioRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ApartamentoControllerTest {

    private Apartamento apartamentoCadastrado;

    private Usuario usuarioAdministrador;

    private final String ENDPOINT_CADASTRO = "/apartamento/api/cadastra";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MoradorRepository moradorRepository;

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    @Autowired
    private ApartamentoRepository apartamentoRepository;

    @Autowired
    private TestDataBuilder testDataBuilder;

    @BeforeEach
    void setUp(){
        var morador = testDataBuilder.getMoradorA();
        var proprietario = testDataBuilder.getProprietarioA();
        morador = moradorRepository.save(morador);
        proprietario = proprietarioRepository.save(proprietario);

        apartamentoCadastrado = testDataBuilder.getApartamentoAtivo(morador, proprietario);
        usuarioAdministrador = testDataBuilder.createUsuarioAdministrador();
    }

    @AfterEach
    void tearDown() {

    }

    @DisplayName("Testa que não pode cadastrar novo apartamento com número e bloco já utilizado")
    @Test
    @Transactional
    void naoDeveCadastrarApartamentoPelaSegundaVez() throws Exception {
        // Arrange
        apartamentoCadastrado = apartamentoRepository.save(apartamentoCadastrado);
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioAdministrador))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"numeroDoApartamento\": " + apartamentoCadastrado.getNumApt() + ", " +
                                                "\"bloco\": \"" + apartamentoCadastrado.getBlocoApt() + "\", " +
                                                "\"nomeDoProprietario\": \"" + apartamentoCadastrado.getProprietario().getNome() + "\", " +
                                                "\"telefoneDoProprietario\": \"" + apartamentoCadastrado.getProprietario().getTelefones().stream().findFirst() + "\", " +
                                                "\"cpfDoProprietario\": \"" + apartamentoCadastrado.getProprietario().getCpf() + "\", " +
                                                "\"emailDoProprietario\": \"" + apartamentoCadastrado.getProprietario().getEmail() + "\", " +
                                                "\"nomeDoMorador\": \"" + apartamentoCadastrado.getMorador().getNome() + "\", " +
                                                "\"telefoneDoMorador\": \"" + apartamentoCadastrado.getMorador().getTelefones().stream().findFirst() + "\", " +
                                                "\"cpfDoMorador\": \"" + apartamentoCadastrado.getMorador().getCpf() + "\", " +
                                                "\"emailDoMorador\": \"" + apartamentoCadastrado.getMorador().getEmail() + "\", " +
                                                "\"telefoneDoProprietario\": \"" + apartamentoCadastrado.getProprietario().getTelefones().stream().findFirst() + "\", " +
                                                "\"metragemDoImovel\": " + apartamentoCadastrado.getMetragem() + "}"
                                )
                )
                // Assert
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Já há um apartamento cadastrado para os dados informados")))
        ;
    }

    @DisplayName("Testa que não pode cadastrar novo apartamento sem preencher o formulario completo")
    @Test
    @Transactional
    void naoDeveCadastrarApartamentoComDadosIncompletos() throws Exception {
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioAdministrador))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"numeroDoApartamento\": " + apartamentoCadastrado.getNumApt() + ", " +
                                                "\"bloco\": \"" + apartamentoCadastrado.getBlocoApt() + "\", " +
                                                "\"nomeDoProprietario\": \"" + apartamentoCadastrado.getProprietario().getNome() + "\", " +
                                                "\"telefoneDoProprietario\": \"" + apartamentoCadastrado.getProprietario().getTelefones().stream().findFirst() + "\", " +
                                                "\"cpfDoProprietario\": \"123456789101\", " +
                                                "\"emailDoProprietario\": \"" + apartamentoCadastrado.getProprietario().getEmail() + "\", " +
                                                "\"nomeDoMorador\": \"" + apartamentoCadastrado.getMorador().getNome() + "\", " +
                                                "\"cpfDoMorador\": \"" + apartamentoCadastrado.getMorador().getCpf() + "\", " +
                                                "\"emailDoMorador\": \"" + apartamentoCadastrado.getMorador().getEmail() + "\", " +
                                                "\"telefoneDoMorador\": \"" + apartamentoCadastrado.getMorador().getTelefones().stream().findFirst() + "\", " +
                                                "\"metragemDoImovel\": " + apartamentoCadastrado.getMetragem() + "}"
                                )
                )
                // Assert
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("CPF do proprietário inválido")))
        ;
    }

    @DisplayName("Testa que não pode cadastrar novo apartamento com algum dado inválido")
    @Test
    @Transactional
    void naoDeveCadastrarApartamentoComDadosInvalidos() throws Exception {
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioAdministrador))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"numeroDoApartamento\": " + apartamentoCadastrado.getNumApt() + ", " +
                                                "\"bloco\": \"" + apartamentoCadastrado.getBlocoApt() + "\", " +
                                                "\"nomeDoProprietario\": \"" + apartamentoCadastrado.getProprietario().getNome() + "\", " +
                                                "\"telefoneDoProprietario\": \"" + apartamentoCadastrado.getProprietario().getTelefones().stream().findFirst() + "\", " +
                                                "\"cpfDoProprietario\": \"" + apartamentoCadastrado.getProprietario().getCpf() + "\", " +
                                                "\"emailDoProprietario\": \"" + apartamentoCadastrado.getProprietario().getEmail() + "\", " +
                                                "\"nomeDoMorador\": \"" + apartamentoCadastrado.getMorador().getNome() + "\", " +
                                                "\"cpfDoMorador\": \"" + apartamentoCadastrado.getMorador().getCpf() + "\", " +
                                                "\"emailDoMorador\": \"" + apartamentoCadastrado.getMorador().getEmail() + "\", " +
                                                "\"telefoneDoProprietario\": \"" + apartamentoCadastrado.getProprietario().getTelefones().stream().findFirst() + "\", " +
                                                "\"metragemDoImovel\": " + apartamentoCadastrado.getMetragem() + "}"
                                )
                )
                // Assert
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("telefone do morador deve ser preenchido")))
        ;
    }

    @DisplayName("Testa cadastro de apartamento com dados válidos")
    @Test
    @Transactional
    void deveCadastrarUsuarioComDadosValidos() throws Exception {
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioAdministrador))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"numeroDoApartamento\": " + apartamentoCadastrado.getNumApt() + ", " +
                                                "\"bloco\": \"" + apartamentoCadastrado.getBlocoApt() + "\", " +
                                                "\"nomeDoProprietario\": \"" + apartamentoCadastrado.getProprietario().getNome() + "\", " +
                                                "\"telefoneDoProprietario\": \"" + apartamentoCadastrado.getProprietario().getTelefones().stream().findFirst() + "\", " +
                                                "\"cpfDoProprietario\": \"" + apartamentoCadastrado.getProprietario().getCpf() + "\", " +
                                                "\"emailDoProprietario\": \"" + apartamentoCadastrado.getProprietario().getEmail() + "\", " +
                                                "\"nomeDoMorador\": \"" + apartamentoCadastrado.getMorador().getNome() + "\", " +
                                                "\"telefoneDoMorador\": \"" + apartamentoCadastrado.getMorador().getTelefones().stream().findFirst() + "\", " +
                                                "\"cpfDoMorador\": \"" + apartamentoCadastrado.getMorador().getCpf() + "\", " +
                                                "\"emailDoMorador\": \"" + apartamentoCadastrado.getMorador().getEmail() + "\", " +
                                                "\"telefoneDoProprietario\": \"" + apartamentoCadastrado.getProprietario().getTelefones().stream().findFirst() + "\", " +
                                                "\"metragemDoImovel\": " + apartamentoCadastrado.getMetragem() + "}"
                                )
                )
                // Assert
                .andExpect(status().isCreated());
    }

}