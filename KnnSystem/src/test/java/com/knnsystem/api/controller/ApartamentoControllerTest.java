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
        apartamentoCadastrado = apartamentoRepository.save(apartamentoCadastrado);
        usuarioAdministrador = testDataBuilder.createUsuarioAdministrador();
    }

    @AfterEach
    void tearDown() {

    }

    @DisplayName("Testa que não pode cadastrar novo apartamento com número e bloco já utilizado")
    @Test
    @Transactional
    void naoDeveCadastrarCpfPelaSegundaVez() throws Exception {
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
                                                "\"cargo\": " + apartamentoCadastrado.getMetragem() + "}"
                                )
                )
                // Assert
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Já há um apartamento cadastrado para os dados informados")))
        ;
    }
}