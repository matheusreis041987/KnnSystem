package com.knnsystem.api.controller;

import com.knnsystem.api.model.entity.DomicilioBancario;
import com.knnsystem.api.model.entity.Fornecedor;
import com.knnsystem.api.model.entity.Responsavel;
import com.knnsystem.api.model.entity.Usuario;
import com.knnsystem.api.model.repository.DomicilioBancarioRepository;
import com.knnsystem.api.model.repository.FornecedorRepository;
import com.knnsystem.api.model.repository.ResponsavelRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class FornecedorControllerTest {

    private Fornecedor fornecedorA;

    private Responsavel responsavelA;

    private DomicilioBancario domicilioBancarioA;

    private Fornecedor fornecedorB;

    private  Responsavel responsavelB;

    private DomicilioBancario domicilioBancarioB;

    private Usuario usuarioSecretaria;

    private Usuario usuarioAdministrador;

    private final String ENDPOINT_CADASTRO = "/fornecedor/api/cadastra";

    private final String ENDPOINT_CONSULTA = "/fornecedor/api/consulta";

    private final String ENDPOINT_INATIVA = "/fornecedor/api/inativa";

    private final String ENDPOINT_EXCLUSAO = "/fornecedor/api/exclui";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ResponsavelRepository responsavelRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    DomicilioBancarioRepository domicilioBancarioRepository;

    @Autowired
    private TestDataBuilder testDataBuilder;

    @BeforeEach
    void setUp(){

        this.domicilioBancarioA = testDataBuilder.createDomicilioBancarioA();
        this.responsavelA = testDataBuilder.createResponsavelA();
        this.fornecedorA = testDataBuilder.createFornecedorA();

        this.domicilioBancarioB = testDataBuilder.createDomicilioBancarioB();
        this.responsavelB = testDataBuilder.createResponsavelB();
        this.fornecedorB = testDataBuilder.createFornecedorB();

        this.usuarioSecretaria = testDataBuilder.createUsuarioSecretaria();
        this.usuarioAdministrador = testDataBuilder.createUsuarioAdministrador();
    }

    @DisplayName("Testa que não pode cadastrar fornecedor mais de uma vez")
    @Test
    @Transactional
    void naoDeveCadastrarFornecedorPelaSegundaVez() throws Exception {
        // Arrange
        responsavelA = responsavelRepository.save(responsavelA);
        fornecedorA.setResponsavel(responsavelA);
        domicilioBancarioA = domicilioBancarioRepository.save(domicilioBancarioA);
        fornecedorA.setDomicilioBancario(domicilioBancarioA);
        fornecedorA = fornecedorRepository.save(fornecedorA);

        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"razaoSocial\": \"" + fornecedorA.getRazaoSocial() + "\", " +
                                                "\"cnpj\": \"" + fornecedorA.getCnpj() + "\", " +
                                                "\"domicilioBancario\": {" +
                                                "\"agencia\": \"" + fornecedorA.getDomicilioBancario().getAgencia() + "\", " +
                                                "\"contaCorrente\": \"" + fornecedorA.getDomicilioBancario().getContaCorrente() + "\", " +
                                                "\"banco\": \"" + fornecedorA.getDomicilioBancario().getBanco() + "\", " +
                                                "\"pix\": \"" + fornecedorA.getDomicilioBancario().getPix() + "\"}, " +
                                                "\"responsavel\": {" +
                                                "\"nome\": \"" + fornecedorA.getResponsavel().getNome() + "\", " +
                                                "\"cpf\": \"" + fornecedorA.getResponsavel().getCpf() + "\", " +
                                                "\"telefone\": \"" + fornecedorA.getResponsavel().getTelefone() + "\", " +
                                                "\"email\": \"" + fornecedorA.getResponsavel().getEmail() + "\"}, " +
                                                "\"enderecoCompleto\": \"" + fornecedorA.getEnderecoCompleto()+ "\", " +
                                                "\"naturezaDoServico\": \"" + fornecedorA.getNaturezaServico() + "\", " +
                                                "\"emailCorporativo\": \"" + fornecedorA.getEmailCorporativo() + "\"}"
                                )
                )
                // Assert
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Já há um fornecedor cadastrado para os dados informados")))
        ;
    }

    @DisplayName("Testa que não pode cadastrar novo fornecedor sem preencher o formulario completo")
    @Test
    @Transactional
    void naoDeveCadastrarFornecedorComDadosIncompletos() throws Exception {
        // Arrange
        fornecedorA.setResponsavel(responsavelA);
        fornecedorA.setDomicilioBancario(domicilioBancarioA);

        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"razaoSocial\": \"" + fornecedorA.getRazaoSocial() + "\", " +
                                                "\"domicilioBancario\": {" +
                                                "\"agencia\": \"" + fornecedorA.getDomicilioBancario().getAgencia() + "\", " +
                                                "\"contaCorrente\": \"" + fornecedorA.getDomicilioBancario().getContaCorrente() + "\", " +
                                                "\"banco\": \"" + fornecedorA.getDomicilioBancario().getBanco() + "\", " +
                                                "\"pix\": \"" + fornecedorA.getDomicilioBancario().getPix() + "\"}, " +
                                                "\"responsavel\": {" +
                                                "\"nome\": \"" + fornecedorA.getResponsavel().getNome() + "\", " +
                                                "\"cpf\": \"" + fornecedorA.getResponsavel().getCpf() + "\", " +
                                                "\"telefone\": \"" + fornecedorA.getResponsavel().getTelefone() + "\", " +
                                                "\"email\": \"" + fornecedorA.getResponsavel().getEmail() + "\"}, " +
                                                "\"enderecoCompleto\": \"" + fornecedorA.getEnderecoCompleto()+ "\", " +
                                                "\"naturezaDoServico\": \"" + fornecedorA.getNaturezaServico() + "\", " +
                                                "\"emailCorporativo\": \"" + fornecedorA.getEmailCorporativo() + "\"}"                                )
                )
                // Assert
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("CNPJ é obrigatório")))
        ;
    }

    @DisplayName("Testa que não pode cadastrar novo fornecedor com algum dado inválido")
    @Test
    @Transactional
    void naoDeveCadastrarFornecedorComDadosInvalidos() throws Exception {
        // Arrange
        fornecedorA.setResponsavel(responsavelA);
        fornecedorA.setDomicilioBancario(domicilioBancarioA);

        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"razaoSocial\": \"" + fornecedorA.getRazaoSocial() + "\", " +
                                                "\"cnpj\": \"00000000000111\", " +
                                                "\"domicilioBancario\": {" +
                                                "\"agencia\": \"" + fornecedorA.getDomicilioBancario().getAgencia() + "\", " +
                                                "\"contaCorrente\": \"" + fornecedorA.getDomicilioBancario().getContaCorrente() + "\", " +
                                                "\"banco\": \"" + fornecedorA.getDomicilioBancario().getBanco() + "\", " +
                                                "\"pix\": \"" + fornecedorA.getDomicilioBancario().getPix() + "\"}, " +
                                                "\"responsavel\": {" +
                                                "\"nome\": \"" + fornecedorA.getResponsavel().getNome() + "\", " +
                                                "\"cpf\": \"" + fornecedorA.getResponsavel().getCpf() + "\", " +
                                                "\"telefone\": \"" + fornecedorA.getResponsavel().getTelefone() + "\", " +
                                                "\"email\": \"" + fornecedorA.getResponsavel().getEmail() + "\"}, " +
                                                "\"enderecoCompleto\": \"" + fornecedorA.getEnderecoCompleto()+ "\", " +
                                                "\"naturezaDoServico\": \"" + fornecedorA.getNaturezaServico() + "\", " +
                                                "\"emailCorporativo\": \"" + fornecedorA.getEmailCorporativo() + "\"}"
                                )
                )
                // Assert
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("CNPJ inválido")))
        ;
    }

    @DisplayName("Testa cadastro de fornecedor com dados válidos")
    @Test
    @Transactional
    void deveCadastrarFornecedorComDadosValidos() throws Exception {
        // Arrange
        responsavelA = responsavelRepository.save(responsavelA);
        fornecedorA.setResponsavel(responsavelA);
        domicilioBancarioA = domicilioBancarioRepository.save(domicilioBancarioA);
        fornecedorA.setDomicilioBancario(domicilioBancarioA);

        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"razaoSocial\": \"" + fornecedorA.getRazaoSocial() + "\", " +
                                                "\"cnpj\": \"" + fornecedorA.getCnpj() + "\", " +
                                                "\"domicilioBancario\": {" +
                                                "\"agencia\": \"" + fornecedorA.getDomicilioBancario().getAgencia() + "\", " +
                                                "\"contaCorrente\": \"" + fornecedorA.getDomicilioBancario().getContaCorrente() + "\", " +
                                                "\"banco\": \"" + fornecedorA.getDomicilioBancario().getBanco() + "\", " +
                                                "\"pix\": \"" + fornecedorA.getDomicilioBancario().getPix() + "\"}, " +
                                                "\"responsavel\": {" +
                                                "\"nome\": \"" + fornecedorA.getResponsavel().getNome() + "\", " +
                                                "\"cpf\": \"" + fornecedorA.getResponsavel().getCpf() + "\", " +
                                                "\"telefone\": \"" + fornecedorA.getResponsavel().getTelefone() + "\", " +
                                                "\"email\": \"" + fornecedorA.getResponsavel().getEmail() + "\"}, " +
                                                "\"enderecoCompleto\": \"" + fornecedorA.getEnderecoCompleto()+ "\", " +
                                                "\"naturezaDoServico\": \"" + fornecedorA.getNaturezaServico() + "\", " +
                                                "\"emailCorporativo\": \"" + fornecedorA.getEmailCorporativo() + "\"}"
                                )
                )
                // Assert
                .andExpect(status().isCreated())
        ;
    }

    @DisplayName("Testa consulta para repositório de fornecedores vazio")
    @Test
    @Transactional
    void deveRetornarErroSeNaoHouverFornecedores() throws Exception {
        // Act
        this.mockMvc.perform(
                        get(ENDPOINT_CONSULTA)
                                .with(user(usuarioSecretaria)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Não existe fornecedor para os dados pesquisados")));

    }

    @DisplayName("Testa consulta quando não encontra cnpj do fornecedor")
    @Test
    @Transactional
    void deveRetornarErroSeNaoHouverFornecedorParaCNPJInformado() throws Exception {
        // Arrange
        responsavelA = responsavelRepository.save(responsavelA);
        fornecedorA.setResponsavel(responsavelA);
        domicilioBancarioA = domicilioBancarioRepository.save(domicilioBancarioA);
        fornecedorA.setDomicilioBancario(domicilioBancarioA);
        fornecedorA = fornecedorRepository.save(fornecedorA);

        // Act
        this.mockMvc.perform(
                        get(ENDPOINT_CONSULTA)
                                .param("cnpj", fornecedorB.getCnpj())
                                .with(user(usuarioSecretaria)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Não existe fornecedor para os dados pesquisados")));

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

        // Act
        this.mockMvc.perform(
                        get(ENDPOINT_CONSULTA)
                                .param("razaoSocial", fornecedorB.getRazaoSocial())
                                .with(user(usuarioSecretaria)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Não existe fornecedor para os dados pesquisados")));

    }

    @DisplayName("Testa consulta quando encontra tanto cnpj quanto número de controle")
    @Test
    @Transactional
    void deveRetornarFornecedoresQuePossuamCnpjOuNumeroControle() throws Exception {
        // Arrange
        responsavelA = responsavelRepository.save(responsavelA);
        fornecedorA.setResponsavel(responsavelA);
        domicilioBancarioA = domicilioBancarioRepository.save(domicilioBancarioA);
        fornecedorA.setDomicilioBancario(domicilioBancarioA);
        fornecedorA = fornecedorRepository.save(fornecedorA);

        responsavelB = responsavelRepository.save(responsavelB);
        fornecedorB.setResponsavel(responsavelB);
        domicilioBancarioB = domicilioBancarioRepository.save(domicilioBancarioB);
        fornecedorB.setDomicilioBancario(domicilioBancarioB);
        fornecedorB = fornecedorRepository.save(fornecedorB);

        // Act
        this.mockMvc.perform(
                        get(ENDPOINT_CONSULTA)
                                .param("cnpj", fornecedorB.getCnpj())
                                .param("numeroControle", fornecedorA.getNumControle().toString())
                                .with(user(usuarioSecretaria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",
                        Matchers.hasSize(2)));

    }

    @DisplayName("Testa inativação de fornecedor dá erro quando não encontra")
    @Test
    @Transactional
    void deveRetornarErroAoTentarInativarFornecedorNaoEncontrado() throws Exception {
        // Arrange
        fornecedorA.setResponsavel(responsavelA);
        fornecedorA.setDomicilioBancario(domicilioBancarioA);

        // Act
        this.mockMvc.perform(
                        put(ENDPOINT_INATIVA + "/1000")
                                .with(user(usuarioSecretaria)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Não existe fornecedor para os dados pesquisados")));

    }

    @DisplayName("Testa inativação de fornecedor com sucesso")
    @Test
    @Transactional
    void deveRetornarDadosDeFornecedorAoInativarComSucesso() throws Exception {
        // Arrange
        responsavelA = responsavelRepository.save(responsavelA);
        fornecedorA.setResponsavel(responsavelA);
        domicilioBancarioA = domicilioBancarioRepository.save(domicilioBancarioA);
        fornecedorA.setDomicilioBancario(domicilioBancarioA);
        fornecedorA = fornecedorRepository.save(fornecedorA);

        // Act
        this.mockMvc.perform(
                        put(ENDPOINT_INATIVA + "/" + fornecedorA.getIdFornecedor())
                                .with(user(usuarioSecretaria)))
                .andExpect(status().isNoContent());

    }

    @DisplayName("Testa exclusão de regristro que não está na base")
    @Test
    @Transactional
    void deveInformarErroAoTentarExcluirRegistroInexistente() throws Exception {
        // Act
        this.mockMvc.perform(
            delete(ENDPOINT_EXCLUSAO + "/123456")
                    .with(user(usuarioAdministrador))
        )
            // Assert
                .andExpect(status().isNotFound());
    }

    @DisplayName("Testa exclusão de regristro que está na base por administrador")
    @Test
    @Transactional
    void deveExcluirRegistroInexistenteSeAdministrador() throws Exception {
        // Arrange
        responsavelA = responsavelRepository.save(responsavelA);
        fornecedorA.setResponsavel(responsavelA);
        domicilioBancarioA = domicilioBancarioRepository.save(domicilioBancarioA);
        fornecedorA.setDomicilioBancario(domicilioBancarioA);
        fornecedorA = fornecedorRepository.save(fornecedorA);
        // Act
        this.mockMvc.perform(
                        delete(ENDPOINT_EXCLUSAO + "/" + fornecedorA.getIdFornecedor())
                                .with(user(usuarioAdministrador))
                )
                // Assert
                .andExpect(status().isNoContent());
    }

    @DisplayName("Testa exclusão de regristro que está na base por secretaria")
    @Test
    @Transactional
    void deveProibirExcluirRegistroInexistenteSeNaoAdministrador() throws Exception {
        // Arrange
        responsavelA = responsavelRepository.save(responsavelA);
        fornecedorA.setResponsavel(responsavelA);
        domicilioBancarioA = domicilioBancarioRepository.save(domicilioBancarioA);
        fornecedorA.setDomicilioBancario(domicilioBancarioA);
        fornecedorA = fornecedorRepository.save(fornecedorA);
        // Act
        this.mockMvc.perform(
                        delete(ENDPOINT_EXCLUSAO + "/" + fornecedorA.getIdFornecedor())
                                .with(user(usuarioSecretaria))
                )
                // Assert
                .andExpect(status().isForbidden());
    }

}