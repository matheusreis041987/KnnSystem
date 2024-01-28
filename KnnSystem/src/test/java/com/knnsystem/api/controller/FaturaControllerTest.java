package com.knnsystem.api.controller;

import com.knnsystem.api.dto.ResultadoPagamentoDTO;
import com.knnsystem.api.exceptions.ErroComunicacaoComBancoException;
import com.knnsystem.api.infrastructure.api.financeiro.ApiInsituicaoFinanceiraService;
import com.knnsystem.api.model.entity.*;
import com.knnsystem.api.model.repository.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class FaturaControllerTest {

    private Fornecedor fornecedorA;

    private Responsavel responsavelA;

    private Sindico sindico;

    private Contrato contratoA;

    private Gestor gestorA;

    private DomicilioBancario domicilioBancarioA;

    private Usuario usuarioSecretaria;

    private final String ENDPOINT_CADASTRO = "/fatura/api/cadastra";

    private final String ENDPOINT_CONSULTA = "/fatura/api/consulta";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApiInsituicaoFinanceiraService apiIF;

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
    private SindicoRepository sindicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

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
        pessoaRepository.save(this.usuarioSecretaria.getPessoa());
        usuarioRepository.save(this.usuarioSecretaria);
    }

    @DisplayName("Testa erro de comunicação com Banco informa erro")
    @Test
    @Transactional
    void deveInformarErroSeHouverComunicacaoComBanco() throws Exception {
        // Arrange
        when(apiIF.efetuarPagamento(any()))
                .thenThrow(
                        new ErroComunicacaoComBancoException(
                                "Operação não realizada - tente mais tarde")
                );
        setDadosContratoA();

        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"numeroContrato\": \"" + contratoA.getNumContrato() + "\", " +
                                                "\"numeroFatura\": 10004321, " +
                                                "\"percentualJuros\": 7.3, " +
                                                "\"cnpjFornecedor\": \"" + fornecedorA.getCnpj() + "\", " +
                                                "\"razaoSocial\": \"" + fornecedorA.getRazaoSocial() + "\", " +
                                                "\"dataCadastro\": \"" + LocalDate.of(2021, 1, 1) + "\", " +
                                                "\"dataPagamento\": \"" + LocalDate.of(2021, 1, 1).plusDays(9) + "\", " +
                                                "\"valor\": 1234.56, " +
                                                "\"domicilioBancario\": {" +
                                                "\"agencia\": \"" + fornecedorA.getDomicilioBancario().getAgencia() + "\", " +
                                                "\"contaCorrente\": \"" + fornecedorA.getDomicilioBancario().getContaCorrente() + "\", " +
                                                "\"banco\": \"" + fornecedorA.getDomicilioBancario().getBanco() + "\", " +
                                                "\"pix\": \"" + fornecedorA.getDomicilioBancario().getPix() + "\"}} "
                                )
                )
                // Assert
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Operação não realizada - tente mais tarde")))
        ;
    }

    @DisplayName("Testa valor acima de 30 mil encaminha pagamento para aprovação")
    @Test
    @Transactional
    void deveAguardarAprovacaoSeValorMaiorQueTrintaMilEntreDezETrintaDias() throws Exception {
        // Arrange
        when(apiIF.efetuarPagamento(any()))
                .thenReturn(
                        new ResultadoPagamentoDTO(
                                StatusPagamento.AGUARDANDO_APROVACAO
                        )
                );
        setDadosContratoA();

        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"numeroContrato\": \"" + contratoA.getNumContrato() + "\", " +
                                                "\"numeroFatura\": 10004321, " +
                                                "\"percentualJuros\": 7.3, " +
                                                "\"cnpjFornecedor\": \"" + fornecedorA.getCnpj() + "\", " +
                                                "\"razaoSocial\": \"" + fornecedorA.getRazaoSocial() + "\", " +
                                                "\"dataCadastro\": \"" + LocalDate.of(2023, 2, 1) + "\", " +
                                                "\"dataPagamento\": \"" + LocalDate.of(2023, 2, 1).plusDays(10) + "\", " +
                                                "\"valor\": 30000.01, " +
                                                "\"domicilioBancario\": {" +
                                                "\"agencia\": \"" + fornecedorA.getDomicilioBancario().getAgencia() + "\", " +
                                                "\"contaCorrente\": \"" + fornecedorA.getDomicilioBancario().getContaCorrente() + "\", " +
                                                "\"banco\": \"" + fornecedorA.getDomicilioBancario().getBanco() + "\", " +
                                                "\"pix\": \"" + fornecedorA.getDomicilioBancario().getPix() + "\"}} "
                                )
                )
                // Assert
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.statusPagamento",
                        Matchers.is("AGUARDANDO_APROVACAO")))
        ;
    }

    @DisplayName("Testa valor acima de 10 mil com vencimento antes de 10 dias corridos")
    @Test
    @Transactional
    void deveNaoPermitirPagamentoAcimaDeDezMilComMenosDeDezDiasDeVencimento() throws Exception {
        // Arrange
        when(apiIF.efetuarPagamento(any()))
                .thenReturn(
                        new ResultadoPagamentoDTO(
                                StatusPagamento.ENVIADO_PARA_PAGAMENTO
                        )
                );
        setDadosContratoA();

        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"numeroContrato\": \"" + contratoA.getNumContrato() + "\", " +
                                                "\"numeroFatura\": 10004321, " +
                                                "\"percentualJuros\": 7.3, " +
                                                "\"cnpjFornecedor\": \"" + fornecedorA.getCnpj() + "\", " +
                                                "\"razaoSocial\": \"" + fornecedorA.getRazaoSocial() + "\", " +
                                                "\"dataCadastro\": \"" + LocalDate.of(2021, 1, 1) + "\", " +
                                                "\"dataPagamento\": \"" + LocalDate.of(2021, 1, 1).plusDays(9) + "\", " +
                                                "\"valor\": 10000.01, " +
                                                "\"domicilioBancario\": {" +
                                                "\"agencia\": \"" + fornecedorA.getDomicilioBancario().getAgencia() + "\", " +
                                                "\"contaCorrente\": \"" + fornecedorA.getDomicilioBancario().getContaCorrente() + "\", " +
                                                "\"banco\": \"" + fornecedorA.getDomicilioBancario().getBanco() + "\", " +
                                                "\"pix\": \"" + fornecedorA.getDomicilioBancario().getPix() + "\"}} "
                                )
                )
                // Assert
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Erro - Pagamentos acima de R$ 10 mil devem ter vencimento entre 10 e 30 dias corridos da data atual")))
        ;
    }

    @DisplayName("Testa valor abaixo de 10 mil com vencimento antes de 10 dias corridos")
    @Test
    @Transactional
    void devePermitirPagamentoAbaixoDeDezMilComMenosDeDezDiasDeVencimento() throws Exception {
        // Arrange
        when(apiIF.efetuarPagamento(any()))
                .thenReturn(
                        new ResultadoPagamentoDTO(
                                StatusPagamento.ENVIADO_PARA_PAGAMENTO
                        )
                );
        setDadosContratoA();

        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"numeroContrato\": \"" + contratoA.getNumContrato() + "\", " +
                                                "\"numeroFatura\": 10004321, " +
                                                "\"percentualJuros\": 7.3, " +
                                                "\"cnpjFornecedor\": \"" + fornecedorA.getCnpj() + "\", " +
                                                "\"razaoSocial\": \"" + fornecedorA.getRazaoSocial() + "\", " +
                                                "\"dataCadastro\": \"" + LocalDate.of(2023, 1, 1) + "\", " +
                                                "\"dataPagamento\": \"" + LocalDate.of(2023, 1, 1).plusDays(9) + "\", " +
                                                "\"valor\": 9999.99, " +
                                                "\"domicilioBancario\": {" +
                                                "\"agencia\": \"" + fornecedorA.getDomicilioBancario().getAgencia() + "\", " +
                                                "\"contaCorrente\": \"" + fornecedorA.getDomicilioBancario().getContaCorrente() + "\", " +
                                                "\"banco\": \"" + fornecedorA.getDomicilioBancario().getBanco() + "\", " +
                                                "\"pix\": \"" + fornecedorA.getDomicilioBancario().getPix() + "\"}} "
                                )
                )
                // Assert
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.statusPagamento",
                        Matchers.is("ENVIADO_PARA_PAGAMENTO")))
        ;
    }

    @DisplayName("Testa número de contrato inválido retorna erro")
    @Test
    @Transactional
    void naoDevePermitirPagamentoParaNumeroDeContratoInvalido() throws Exception {
        // Arrange
        when(apiIF.efetuarPagamento(any()))
                .thenReturn(
                        new ResultadoPagamentoDTO(
                                StatusPagamento.ENVIADO_PARA_PAGAMENTO
                        )
                );
        setDadosContratoA();

        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"numeroContrato\": \"123789456\", " +
                                                "\"numeroFatura\": 10004321, " +
                                                "\"percentualJuros\": 7.3, " +
                                                "\"cnpjFornecedor\": \"" + fornecedorA.getCnpj() + "\", " +
                                                "\"razaoSocial\": \"" + fornecedorA.getRazaoSocial() + "\", " +
                                                "\"dataCadastro\": \"" + LocalDate.of(2022, 1, 1) + "\", " +
                                                "\"dataPagamento\": \"" + LocalDate.of(2022, 1, 1).plusDays(11) + "\", " +
                                                "\"valor\": 9999.99, " +
                                                "\"domicilioBancario\": {" +
                                                "\"agencia\": \"" + fornecedorA.getDomicilioBancario().getAgencia() + "\", " +
                                                "\"contaCorrente\": \"" + fornecedorA.getDomicilioBancario().getContaCorrente() + "\", " +
                                                "\"banco\": \"" + fornecedorA.getDomicilioBancario().getBanco() + "\", " +
                                                "\"pix\": \"" + fornecedorA.getDomicilioBancario().getPix() + "\"}} "
                                )
                )
                // Assert
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Não há contrato para o número informado")))
        ;
    }

    @DisplayName("Testa vencimento abaixo de 5 dias úteis")
    @Test
    @Transactional
    void naoDevePermitirPagamentoComMenosDeCincoDiasUteis() throws Exception {
        // Arrange
        when(apiIF.efetuarPagamento(any()))
                .thenReturn(
                        new ResultadoPagamentoDTO(
                                StatusPagamento.ENVIADO_PARA_PAGAMENTO
                        )
                );
        setDadosContratoA();

        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"numeroContrato\": \"" + contratoA.getNumContrato() + "\", " +
                                                "\"numeroFatura\": 10004321, " +
                                                "\"percentualJuros\": 7.3, " +
                                                "\"cnpjFornecedor\": \"" + fornecedorA.getCnpj() + "\", " +
                                                "\"razaoSocial\": \"" + fornecedorA.getRazaoSocial() + "\", " +
                                                "\"dataPagamento\": \"" + LocalDate.now() + "\", " +
                                                "\"valor\": 9999.99, " +
                                                "\"domicilioBancario\": {" +
                                                "\"agencia\": \"" + fornecedorA.getDomicilioBancario().getAgencia() + "\", " +
                                                "\"contaCorrente\": \"" + fornecedorA.getDomicilioBancario().getContaCorrente() + "\", " +
                                                "\"banco\": \"" + fornecedorA.getDomicilioBancario().getBanco() + "\", " +
                                                "\"pix\": \"" + fornecedorA.getDomicilioBancario().getPix() + "\"}} "
                                )
                )
                // Assert
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Não pode haver pagamento com menos de 5 dias úteis")))
        ;
    }

    @DisplayName("Testa vencimento fora do mês")
    @Test
    @Transactional
    void naoDevePermitirPagamentoComVencimentoAposOMesCorrente() throws Exception {
        // Arrange
        when(apiIF.efetuarPagamento(any()))
                .thenReturn(
                        new ResultadoPagamentoDTO(
                                StatusPagamento.ENVIADO_PARA_PAGAMENTO
                        )
                );
        setDadosContratoA();

        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"numeroContrato\": \"" + contratoA.getNumContrato() + "\", " +
                                                "\"numeroFatura\": 10004321, " +
                                                "\"percentualJuros\": 7.3, " +
                                                "\"cnpjFornecedor\": \"" + fornecedorA.getCnpj() + "\", " +
                                                "\"razaoSocial\": \"" + fornecedorA.getRazaoSocial() + "\", " +
                                                "\"dataPagamento\": \"" + LocalDate.now().plusMonths(1) + "\", " +
                                                "\"valor\": 9999.99, " +
                                                "\"domicilioBancario\": {" +
                                                "\"agencia\": \"" + fornecedorA.getDomicilioBancario().getAgencia() + "\", " +
                                                "\"contaCorrente\": \"" + fornecedorA.getDomicilioBancario().getContaCorrente() + "\", " +
                                                "\"banco\": \"" + fornecedorA.getDomicilioBancario().getBanco() + "\", " +
                                                "\"pix\": \"" + fornecedorA.getDomicilioBancario().getPix() + "\"}} "
                                )
                )
                // Assert
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Erro - favor escolher uma data futura do mês corrente")))
        ;
    }

    @DisplayName("Testa vencimento no passado")
    @Test
    @Transactional
    void naoDevePermitirPagamentoComVencimentoNoPassado() throws Exception {
        // Arrange
        when(apiIF.efetuarPagamento(any()))
                .thenReturn(
                        new ResultadoPagamentoDTO(
                                StatusPagamento.ENVIADO_PARA_PAGAMENTO
                        )
                );
        setDadosContratoA();

        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"numeroContrato\": \"" + contratoA.getNumContrato() + "\", " +
                                                "\"numeroFatura\": 10004321, " +
                                                "\"percentualJuros\": 7.3, " +
                                                "\"cnpjFornecedor\": \"" + fornecedorA.getCnpj() + "\", " +
                                                "\"razaoSocial\": \"" + fornecedorA.getRazaoSocial() + "\", " +
                                                "\"dataPagamento\": \"" + LocalDate.now().plusDays(-1) + "\", " +
                                                "\"valor\": 9999.99, " +
                                                "\"domicilioBancario\": {" +
                                                "\"agencia\": \"" + fornecedorA.getDomicilioBancario().getAgencia() + "\", " +
                                                "\"contaCorrente\": \"" + fornecedorA.getDomicilioBancario().getContaCorrente() + "\", " +
                                                "\"banco\": \"" + fornecedorA.getDomicilioBancario().getBanco() + "\", " +
                                                "\"pix\": \"" + fornecedorA.getDomicilioBancario().getPix() + "\"}} "
                                )
                )
                // Assert
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Erro - favor escolher uma data futura")))
        ;
    }

    @DisplayName("Testa consulta para repositório de faturas vazio")
    @Test
    @Transactional
    void deveRetornarErroSeNaoHouverFaturas() throws Exception {
        // Act
        this.mockMvc.perform(
                        get(ENDPOINT_CONSULTA)
                                .with(user(usuarioSecretaria)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Erro - fatura não encontrada para os dados apresentados")));

    }

    @DisplayName("Testa consulta quando não encontra cnpj do fornecedor")
    @Test
    @Transactional
    void deveRetornarErroSeNaoHouverFaturaParaCNPJInformado() throws Exception {
        // Arrange
        setDadosContratoA();
        // Act
        this.mockMvc.perform(
                        get(ENDPOINT_CONSULTA)
                                .param("cnpjFornecedor", "68308725000185")
                                .with(user(usuarioSecretaria)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Erro - fatura não encontrada para os dados apresentados")));

    }

    @DisplayName("Testa consulta quando não encontra numero de contrato")
    @Test
    @Transactional
    void deveRetornarErroSeNaoHouverFaturaParaNumeroDeContrato() throws Exception {
        // Arrange
        setDadosContratoA();
        // Act
        this.mockMvc.perform(
                        get(ENDPOINT_CONSULTA)
                                .param("numeroContrato", "68308725000185")
                                .with(user(usuarioSecretaria)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Erro - fatura não encontrada para os dados apresentados")));

    }

    private void setDadosContratoA(){
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
    }

}