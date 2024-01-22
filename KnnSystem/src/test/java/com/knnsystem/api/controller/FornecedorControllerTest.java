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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class FornecedorControllerTest {

    private Fornecedor fornecedorA;

    private Responsavel responsavelA;

    private DomicilioBancario domicilioBancarioA;

    private Usuario usuarioSecretaria;

    private final String ENDPOINT_CADASTRO = "/fornecedor/api/cadastra";

    private final String ENDPOINT_CONSULTA = "/fornecedor/api/consulta";

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
        this.usuarioSecretaria = testDataBuilder.createUsuarioSecretaria();
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

}