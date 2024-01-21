package com.knnsystem.api.controller;

import com.knnsystem.api.model.entity.Fornecedor;
import com.knnsystem.api.model.entity.Usuario;
import com.knnsystem.api.model.repository.FornecedorRepository;
import org.hamcrest.Matchers;
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
class FornecedorControllerTest {

    private Fornecedor fornecedorA;

    private Usuario usuarioSecretaria;

    private final String ENDPOINT_CADASTRO = "/fornecedor/api/cadastra";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    private TestDataBuilder testDataBuilder;

    @BeforeEach
    void setUp(){
        this.fornecedorA = testDataBuilder.createFornecedorA();
        this.usuarioSecretaria = testDataBuilder.createUsuarioSecretaria();
    }

    @DisplayName("Testa que não pode cadastrar morador mais de uma vez")
    @Test
    @Transactional
    void naoDeveCadastrarMoradorPelaSegundaVez() throws Exception {
        // Arrange
        fornecedorA = fornecedorRepository.save(fornecedorA);
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"razaoSocial\": \"" + fornecedorA.getRazaoSocial() + "\", " +
                                                "\"cnpj\": \"" + fornecedorA.getCnpj() + "\", " +
                                                "\"domicilioBancario\": " + fornecedorA.getDomicilioBancario().getIdDomicilio() + ", " +
                                                "\"nomeDoResponsavel\": \"" + fornecedorA.getResponsavel().getNome() + "\", " +
                                                "\"cpfDoResponsavel\": \"" + fornecedorA.getResponsavel().getCpf() + "\", " +
                                                "\"emailDoResponsavel\": \"" + fornecedorA.getResponsavel().getEmail() + "\", " +
                                                "\"emailCorporativo\": \"" + fornecedorA.getEmailCorporativo() + "\", " +
                                                "\"enderecoCompleto\": \"" + fornecedorA.getEnderecoCompleto() + "\", " +
                                                "\"naturezaDoServico\": \"" + fornecedorA.getNaturezaServico() + "\"}"
                                )
                )
                // Assert
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Já há um fornecedor cadastrado para os dados informados")))
        ;
    }

}