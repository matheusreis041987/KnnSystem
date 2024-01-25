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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    private Fornecedor fornecedorB;

    private Responsavel responsavelA;

    private DomicilioBancario domicilioBancarioA;

    private Usuario usuarioSecretaria;

    private final String ENDPOINT_CADASTRO = "/contrato/api/cadastra";

    private final String ENDPOINT_CONSULTA = "/contrato/api/consulta";

    private final String ENDPOINT_INATIVA = "/contrato/api/inativa";

    private final String ENDPOINT_REAJUSTA = "/contrato/api/reajusta";

    private final String ENDPOINT_RESCINDE = "/contrato/api/rescinde";

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
        this.fornecedorB = testDataBuilder.createFornecedorB();


        this.usuarioSecretaria = testDataBuilder.createUsuarioSecretaria();
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
                                        "{\"numeroControleFornecedor\": \"" + contratoA.getFornecedor().getNumControle() + "\", " +
                                                "\"vigenciaInicial\": \"" + contratoA.getVigenciaInicial() + "\", " +
                                                "\"vigenciaFinal\": \"" + contratoA.getVigenciaFinal() + "\", " +
                                                "\"valorMensalAtual\": " + contratoA.getValorMensalAtual() + ", " +
                                                "\"valorMensalInicial\": " + contratoA.getValorMensalInicial() + ", " +
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
                        Matchers.is("objeto contratual é obrigatório")))
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
                                        "{\"numeroControleFornecedor\": \"" + contratoA.getFornecedor().getNumControle() + "\", " +
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

    @DisplayName("Testa cadastro de contrato com dados válidos")
    @Test
    @Transactional
    void deveCadastrarContratoComDadosValidos() throws Exception {
        // Arrange
        responsavelA = responsavelRepository.save(responsavelA);
        fornecedorA.setResponsavel(responsavelA);
        domicilioBancarioA = domicilioBancarioRepository.save(domicilioBancarioA);
        fornecedorA.setDomicilioBancario(domicilioBancarioA);
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
                                        "{\"numeroControleFornecedor\": \"" + contratoA.getFornecedor().getNumControle() + "\", " +
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
                .andExpect(status().isCreated())
        ;
    }

    @DisplayName("Testa consulta para repositório de contratos vazio")
    @Test
    @Transactional
    void deveRetornarErroSeNaoHouverContrato() throws Exception {
        // Act
        this.mockMvc.perform(
                        get(ENDPOINT_CONSULTA)
                                .with(user(usuarioSecretaria)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Não há um contrato cadastrado para os dados informados")));

    }

    @DisplayName("Testa consulta quando não encontra cnpj do fornecedor")
    @Test
    @Transactional
    void deveRetornarErroSeNaoHouverContratoParaCNPJInformado() throws Exception {
        // Arrange
        responsavelA = responsavelRepository.save(responsavelA);
        fornecedorA.setResponsavel(responsavelA);
        domicilioBancarioA = domicilioBancarioRepository.save(domicilioBancarioA);
        fornecedorA.setDomicilioBancario(domicilioBancarioA);
        fornecedorA = fornecedorRepository.save(fornecedorA);
        gestorA = gestorRepository.save(gestorA);
        sindico = sindicoRepository.save(sindico);
        contratoA.setFornecedor(fornecedorA);
        contratoA.setGestor(gestorA);
        contratoA.setSindico(sindico);
        contratoRepository.save(contratoA);

        // Act
        this.mockMvc.perform(
                        get(ENDPOINT_CONSULTA)
                                .param("cnpjFornecedor", fornecedorB.getCnpj())
                                .with(user(usuarioSecretaria)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Não há um contrato cadastrado para os dados informados")));

    }

    @DisplayName("Testa consulta quando não encontra razão social do fornecedor")
    @Test
    @Transactional
    void deveRetornarErroSeNaoHouverFornecedorParaRazaoSocialInformada() throws Exception {
        // Arrange
        responsavelA = responsavelRepository.save(responsavelA);
        fornecedorA.setResponsavel(responsavelA);
        domicilioBancarioA = domicilioBancarioRepository.save(domicilioBancarioA);
        fornecedorA.setDomicilioBancario(domicilioBancarioA);
        fornecedorA = fornecedorRepository.save(fornecedorA);
        gestorA = gestorRepository.save(gestorA);
        sindico = sindicoRepository.save(sindico);
        contratoA.setFornecedor(fornecedorA);
        contratoA.setGestor(gestorA);
        contratoA.setSindico(sindico);
        contratoRepository.save(contratoA);

        // Act
        this.mockMvc.perform(
                        get(ENDPOINT_CONSULTA)
                                .param("razaoSocial", fornecedorB.getRazaoSocial())
                                .with(user(usuarioSecretaria)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Não há um contrato cadastrado para os dados informados")));

    }

    @DisplayName("Testa consulta quando encontra tanto cnpj quanto número de contrato")
    @Test
    @Transactional
    void deveRetornarContratosQuePossuamParametros() throws Exception {
        // Arrange
        responsavelA = responsavelRepository.save(responsavelA);
        fornecedorA.setResponsavel(responsavelA);
        domicilioBancarioA = domicilioBancarioRepository.save(domicilioBancarioA);
        fornecedorA.setDomicilioBancario(domicilioBancarioA);
        fornecedorA = fornecedorRepository.save(fornecedorA);
        gestorA = gestorRepository.save(gestorA);
        sindico = sindicoRepository.save(sindico);
        contratoA.setFornecedor(fornecedorA);
        contratoA.setGestor(gestorA);
        contratoA.setSindico(sindico);
        contratoRepository.save(contratoA);

        // Act
        this.mockMvc.perform(
                        get(ENDPOINT_CONSULTA)
                                .param("cnpjFornecedor", fornecedorB.getCnpj())
                                .param("razaoSocial", fornecedorA.getRazaoSocial())
                                .with(user(usuarioSecretaria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",
                        Matchers.hasSize(1)));

    }

    @DisplayName("Testa inativação de contrato dá erro quando não encontra")
    @Test
    @Transactional
    void deveRetornarErroAoTentarInativarContratoNaoEncontrado() throws Exception {
        // Act
        this.mockMvc.perform(
                        put(ENDPOINT_INATIVA + "/1")
                                .with(user(usuarioSecretaria)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Não há um contrato cadastrado para os dados informados")));

    }

    @DisplayName("Testa reajuste de contrato dá erro quando não encontra")
    @Test
    @Transactional
    void deveRetornarErroAoTentarReajustarContratoNaoEncontrado() throws Exception {
        // Act
        this.mockMvc.perform(
                        put(ENDPOINT_REAJUSTA + "/1")
                                .with(user(usuarioSecretaria))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"ipcaAcumulado\": 4.56, " +
                                "\"data\": \"2023-11-30\"}"
                )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Não há um contrato cadastrado para os dados informados")));

    }

    @DisplayName("Testa reajuste de contrato dá erro quando data inferior a um ano da data inicial da vigência")
    @Test
    @Transactional
    void deveRetornarErroAoTentarReajustarContratoAntesDeUmAnoDaVigenciaInicial() throws Exception {
        // Arrange
        responsavelA = responsavelRepository.save(responsavelA);
        fornecedorA.setResponsavel(responsavelA);
        domicilioBancarioA = domicilioBancarioRepository.save(domicilioBancarioA);
        fornecedorA.setDomicilioBancario(domicilioBancarioA);
        fornecedorA = fornecedorRepository.save(fornecedorA);
        gestorA = gestorRepository.save(gestorA);
        sindico = sindicoRepository.save(sindico);
        contratoA.setFornecedor(fornecedorA);
        contratoA.setGestor(gestorA);
        contratoA.setSindico(sindico);
        contratoRepository.save(contratoA);

        // Act
        this.mockMvc.perform(
                        put(ENDPOINT_REAJUSTA + "/" + contratoA.getIdContrato())
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"ipcaAcumulado\": 4.56, " +
                                                "\"data\": \"" + contratoA.getVigenciaInicial().plusYears(1).plusDays(-1) + "\"}"
                                )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Erro - Data inválida. O reajuste é feito anualmente")));

    }

    @DisplayName("Testa reajuste de contrato com sucesso quando data superior a um ano da data inicial da vigência")
    @Test
    @Transactional
    void deveTerSucessoAoTentarReajustarContratoAposUmAnoDaVigenciaInicial() throws Exception {
        // Arrange
        responsavelA = responsavelRepository.save(responsavelA);
        fornecedorA.setResponsavel(responsavelA);
        domicilioBancarioA = domicilioBancarioRepository.save(domicilioBancarioA);
        fornecedorA.setDomicilioBancario(domicilioBancarioA);
        fornecedorA = fornecedorRepository.save(fornecedorA);
        gestorA = gestorRepository.save(gestorA);
        sindico = sindicoRepository.save(sindico);
        contratoA.setFornecedor(fornecedorA);
        contratoA.setGestor(gestorA);
        contratoA.setSindico(sindico);
        contratoRepository.save(contratoA);

        // Act
        this.mockMvc.perform(
                        put(ENDPOINT_REAJUSTA + "/" + contratoA.getIdContrato())
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"ipcaAcumulado\": 4.56, " +
                                                "\"data\": \"" + contratoA.getVigenciaInicial().plusYears(1).plusDays(1) + "\"}"
                                )
                )
                .andExpect(status().isNoContent());

    }


    @DisplayName("Testa rescisão de contrato dá erro quando não encontra")
    @Test
    @Transactional
    void deveRetornarErroAoTentarRescindirContratoNaoEncontrado() throws Exception {
        // Act
        this.mockMvc.perform(
                        put(ENDPOINT_RESCINDE + "/1")
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"causador\": \"FORNECEDOR\", " +
                                                "\"dataRescisao\": \"2024-01-15\"}"
                                )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Não há um contrato cadastrado para os dados informados")));

    }

    @DisplayName("Testa rescisão de contrato dá erro quando informa causador não permitido")
    @Test
    @Transactional
    void deveRetornarErroAoTentarRescindirContratoInformandoCausadorInvalido() throws Exception {
        // Arrange
        responsavelA = responsavelRepository.save(responsavelA);
        fornecedorA.setResponsavel(responsavelA);
        domicilioBancarioA = domicilioBancarioRepository.save(domicilioBancarioA);
        fornecedorA.setDomicilioBancario(domicilioBancarioA);
        fornecedorA = fornecedorRepository.save(fornecedorA);
        gestorA = gestorRepository.save(gestorA);
        sindico = sindicoRepository.save(sindico);
        contratoA.setFornecedor(fornecedorA);
        contratoA.setGestor(gestorA);
        contratoA.setSindico(sindico);
        contratoRepository.save(contratoA);

        // Act
        this.mockMvc.perform(
                        put(ENDPOINT_RESCINDE + "/" + contratoA.getIdContrato())
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"causador\": \"VENDEDOR\", " +
                                                "\"dataRescisao\": \"2024-01-15\"}"
                                )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Causador deve ser 'FORNECEDOR' ou 'CONTRATANTE'")));

    }

}
