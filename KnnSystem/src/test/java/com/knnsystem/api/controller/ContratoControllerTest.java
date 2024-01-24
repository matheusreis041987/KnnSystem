package com.knnsystem.api.controller;

import com.knnsystem.api.model.entity.*;
import com.knnsystem.api.model.repository.*;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ContratoControllerTest {

    private Sindico sindico;

    private Contrato contratoA;

    private Gestor gestorA;

    private Fornecedor fornecedorA;

    private Responsavel responsavelA;

    private DomicilioBancario domicilioBancarioA;

    private Usuario usuarioSecretaria;

    private final String ENDPOINT_CADASTRO = "/contrato/api/cadastra";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SindicoRepository sindicoRepository;

    @Autowired
    ResponsavelRepository responsavelRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    DomicilioBancarioRepository domicilioBancarioRepository;

    @Autowired
    GestorRepository gestorRepository;

    @Autowired
    ContratoRepository contratoRepository;

    @Autowired
    private TestDataBuilder testDataBuilder;

    @BeforeEach
    void setUp(){

        this.domicilioBancarioA = testDataBuilder.createDomicilioBancarioA();
        this.responsavelA = testDataBuilder.createResponsavelA();
        this.fornecedorA = testDataBuilder.createFornecedorA();
        this.gestorA = testDataBuilder.createGestorA();
        this.sindico = testDataBuilder.createSindico();
        this.contratoA = testDataBuilder.createContratoA();


        this.usuarioSecretaria = testDataBuilder.createUsuarioSecretaria();
    }

    @DisplayName("Testa que não pode cadastrar contrato mais de uma vez")
    @Test
    @Transactional
    void naoDeveCadastrarContratoPelaSegundaVez() throws Exception {
        // Arrange
        responsavelA = responsavelRepository.save(responsavelA);
        fornecedorA.setResponsavel(responsavelA);
        domicilioBancarioA = domicilioBancarioRepository.save(domicilioBancarioA);
        fornecedorA.setDomicilioBancario(domicilioBancarioA);
        fornecedorA.geraNumeroDeControle();
        fornecedorA = fornecedorRepository.save(fornecedorA);
        gestorA = gestorRepository.save(gestorA);
        sindico = sindicoRepository.save(sindico);
        contratoA.setFornecedor(fornecedorA);
        contratoA.setGestor(gestorA);
        contratoA.setSindico(sindico);
        contratoRepository.save(contratoA);

        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"numeroContrato\": \"" + contratoA.getNumContrato() + "\", " +
                                                "\"numeroControleFornecedor\": \"" + contratoA.getFornecedor().getNumControle() + "\", " +
                                                "\"vigenciaInicial\": \"" + contratoA.getVigenciaInicial() + "\", " +
                                                "\"vigenciaFinal\": \"" + contratoA.getVigenciaFinal() + "\", " +
                                                "\"valorMensalAtual\": " + contratoA.getValorMensalAtual() + ", " +
                                                "\"valorMensalInicial\": " + contratoA.getValorMensalInicial() + ", " +
                                                "\"objetoContratual\": \"" + contratoA.getObjetoContratual() + "\", " +
                                                "\"gestor\": {" +
                                                "\"nome\": \"" + contratoA.getGestor().getNome() + "\", " +
                                                "\"cpf\": \"" + contratoA.getGestor().getCpf() + "\", " +
                                                "\"email\": \"" + contratoA.getGestor().getEmail() + "\"}, " +
                                                "\"emailSindico\": \"" + contratoA.getSindico().getEmail() + "\", " +
                                                "\"percentualMulta\": " + contratoA.getPercMulta() + "}"
                                )
                )
                // Assert
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Já há um contrato cadastrado para os dados informados")))
        ;
    }

    @DisplayName("Testa que não pode cadastrar novo contrato sem preencher o formulario completo")
    @Test
    @Transactional
    void naoDeveCadastrarContratoComDadosIncompletos() throws Exception {
        // Arrange
        responsavelA = responsavelRepository.save(responsavelA);
        fornecedorA.setResponsavel(responsavelA);
        domicilioBancarioA = domicilioBancarioRepository.save(domicilioBancarioA);
        fornecedorA.setDomicilioBancario(domicilioBancarioA);
        fornecedorA.geraNumeroDeControle();
        fornecedorA = fornecedorRepository.save(fornecedorA);
        gestorA = gestorRepository.save(gestorA);
        sindico = sindicoRepository.save(sindico);
        contratoA.setFornecedor(fornecedorA);
        contratoA.setGestor(gestorA);
        contratoA.setSindico(sindico);

        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"numeroContrato\": \"" + contratoA.getNumContrato() + "\", " +
                                                "\"vigenciaInicial\": \"" + contratoA.getVigenciaInicial() + "\", " +
                                                "\"vigenciaFinal\": \"" + contratoA.getVigenciaFinal() + "\", " +
                                                "\"valorMensalAtual\": " + contratoA.getValorMensalAtual() + ", " +
                                                "\"valorMensalInicial\": " + contratoA.getValorMensalInicial() + ", " +
                                                "\"objetoContratual\": \"" + contratoA.getObjetoContratual() + "\", " +
                                                "\"gestor\": {" +
                                                "\"nome\": \"" + contratoA.getGestor().getNome() + "\", " +
                                                "\"cpf\": \"" + contratoA.getGestor().getCpf() + "\", " +
                                                "\"email\": \"" + contratoA.getGestor().getEmail() + "\"}, " +
                                                "\"emailSindico\": \"" + contratoA.getSindico().getEmail() + "\", " +
                                                "\"percentualMulta\": " + contratoA.getPercMulta() + "}"
                                )
                )
                // Assert
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Número de controle do fornecedor é obrigatório")))
        ;
    }

    @DisplayName("Testa que não pode cadastrar novo contrato com algum dado inválido")
    @Test
    @Transactional
    void naoDeveCadastrarContratoComDadosInvalidos() throws Exception {
        // Arrange
        responsavelA = responsavelRepository.save(responsavelA);
        fornecedorA.setResponsavel(responsavelA);
        domicilioBancarioA = domicilioBancarioRepository.save(domicilioBancarioA);
        fornecedorA.setDomicilioBancario(domicilioBancarioA);
        fornecedorA.geraNumeroDeControle();
        fornecedorA = fornecedorRepository.save(fornecedorA);
        gestorA = gestorRepository.save(gestorA);
        sindico = sindicoRepository.save(sindico);
        contratoA.setFornecedor(fornecedorA);
        contratoA.setGestor(gestorA);
        contratoA.setSindico(sindico);        // Arrange
        responsavelA = responsavelRepository.save(responsavelA);
        fornecedorA.setResponsavel(responsavelA);
        domicilioBancarioA = domicilioBancarioRepository.save(domicilioBancarioA);
        fornecedorA.setDomicilioBancario(domicilioBancarioA);
        fornecedorA.geraNumeroDeControle();
        fornecedorA = fornecedorRepository.save(fornecedorA);
        gestorA = gestorRepository.save(gestorA);
        sindico = sindicoRepository.save(sindico);
        contratoA.setFornecedor(fornecedorA);
        contratoA.setGestor(gestorA);
        contratoA.setSindico(sindico);

        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"numeroContrato\": \"" + contratoA.getNumContrato() + "\", " +
                                                "\"numeroControleFornecedor\": \"" + contratoA.getFornecedor().getNumControle() + "\", " +
                                                "\"vigenciaInicial\": \"" + contratoA.getVigenciaInicial() + "\", " +
                                                "\"vigenciaFinal\": \"" + contratoA.getVigenciaFinal() + "\", " +
                                                "\"valorMensalAtual\": " + contratoA.getValorMensalAtual() + ", " +
                                                "\"valorMensalInicial\": " + contratoA.getValorMensalInicial() + ", " +
                                                "\"objetoContratual\": \"" + contratoA.getObjetoContratual() + "\", " +
                                                "\"gestor\": {" +
                                                "\"nome\": \"" + contratoA.getGestor().getNome() + "\", " +
                                                "\"cpf\": \"11111111111\", " +
                                                "\"email\": \"" + contratoA.getGestor().getEmail() + "\"}, " +
                                                "\"emailSindico\": \"" + contratoA.getSindico().getEmail() + "\", " +
                                                "\"percentualMulta\": " + contratoA.getPercMulta() + "}"
                                )
                )
                // Assert
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("CPF inválido")))
        ;
    }

}