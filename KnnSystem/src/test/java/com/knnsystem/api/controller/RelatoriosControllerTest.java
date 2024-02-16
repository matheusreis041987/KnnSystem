package com.knnsystem.api.controller;

import com.knnsystem.api.model.entity.Usuario;
import com.knnsystem.api.model.repository.ApartamentoRepository;
import com.knnsystem.api.model.repository.MoradorRepository;
import com.knnsystem.api.model.repository.PessoaRepository;
import com.knnsystem.api.model.repository.ProprietarioRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class RelatoriosControllerTest {

    private Usuario usuarioSindico;

    private Usuario usuarioAdministrador;

    private Usuario usuarioSecretaria;

    private final String ENDPOINT_RELATORIO_APARTAMENTOS = "/relatorio/api/apartamentos";

    private final String ENDPOINT_RELATORIO_CONTRATOS_VIGENTES = "/relatorio/api/contratos-vigentes";

    private final String ENDPOINT_RELATORIO_CONTRATOS_VENCIDOS = "/relatorio/api/contratos-vencidos";

    private final String ENDPOINT_RELATORIO_FORNECEDORES_ATIVOS = "/relatorio/api/fornecedores-ativos";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataBuilder testDataBuilder;

    @Autowired
    private ApartamentoRepository apartamentoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private MoradorRepository moradorRepository;

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    @BeforeEach
    void setUp(){

        usuarioSindico = testDataBuilder.createUsuarioSindico();
        usuarioAdministrador = testDataBuilder.createUsuarioAdministrador();
        usuarioSecretaria = testDataBuilder.createUsuarioSecretaria();
    }

    @AfterEach
    void tearDown(){
    }

    @DisplayName("testa relatório de apartamentos sem resultados retorna erro")
    @Test
    @Transactional
    void deveLancarErroRelatorioDeApartamentosSeNaoHouverResultados() throws Exception {
        // Act
        this.mockMvc
                .perform(
                        get(ENDPOINT_RELATORIO_APARTAMENTOS)
                                .with(user(usuarioSindico))
                )
                // Assert
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Erro -  não há dados para o relatório")))
        ;
    }

    @DisplayName("testa relatório de apartamento com um resultado")
    @Test
    @Transactional
    void deveRetornarUnicoApartamentoSeForUnico() throws Exception {
        // Arrange
        var morador = testDataBuilder.createMoradorA();
        var proprietario = testDataBuilder.createProprietarioA();
        var apartamento = testDataBuilder.createApartamentoAtivo(morador, proprietario);

        moradorRepository.save(morador);
        proprietarioRepository.save(proprietario);
        apartamentoRepository.save(apartamento);

        // Act
        this.mockMvc
                .perform(
                        get(ENDPOINT_RELATORIO_APARTAMENTOS)
                                .with(user(usuarioSindico))
                )
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",
                        hasSize(1)))
                .andExpect(jsonPath("$[0].morador.numeroDoApartamento",
                        Matchers.is(apartamento.getNumApt())))
                .andExpect(jsonPath("$[0].morador.blocoDoApartamento",
                        Matchers.is(apartamento.getBlocoApt())))
                .andExpect(jsonPath("$[0].proprietario.nome",
                        Matchers.is(proprietario.getNome())))
                .andExpect(jsonPath("$[0].morador.nome",
                        Matchers.is(morador.getNome())))
                .andExpect(jsonPath("$[0].proprietario.cpf",
                        Matchers.is(proprietario.getCpf())))
                .andExpect(jsonPath("$[0].morador.cpf",
                        Matchers.is(morador.getCpf())))
                .andExpect(jsonPath("$[0].proprietario.email",
                        Matchers.is(proprietario.getEmail())))
                .andExpect(jsonPath("$[0].morador.email",
                        Matchers.is(morador.getEmail())))
                .andExpect(jsonPath("$[0].metragemDoImovel",
                        Matchers.is(apartamento.getMetragem())))
        ;
    }

    @DisplayName("testa relatório de apartamento com mais resultados")
    @Test
    @Transactional
    void deveRetornarMaisApartamentosSeHouver() throws Exception {
        // Arrange
        var morador = testDataBuilder.createMoradorA();
        var proprietario = testDataBuilder.createProprietarioA();
        moradorRepository.save(morador);
        proprietarioRepository.save(proprietario);

        var apartamento = testDataBuilder.createApartamentoAtivo(morador, proprietario);
        apartamentoRepository.save(apartamento);

        morador = testDataBuilder.createMoradorB();
        proprietario = testDataBuilder.createProprietarioB();
        apartamento = testDataBuilder.createApartamentoAtivo(morador, proprietario);

        moradorRepository.save(morador);
        proprietarioRepository.save(proprietario);
        apartamentoRepository.save(apartamento);

        // Act
        this.mockMvc
                .perform(
                        get(ENDPOINT_RELATORIO_APARTAMENTOS)
                                .with(user(usuarioAdministrador))
                )
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",
                        hasSize(2)));
    }

    @DisplayName("testa perfil secretaria nao visualiza relatorios")
    @Test
    @Transactional
    void deveProibirSecretariaDeVisualizarRelatorioDeApartamentos() throws Exception {
        // Act
        this.mockMvc
                .perform(
                        get(ENDPOINT_RELATORIO_APARTAMENTOS)
                                .with(user(usuarioSecretaria))
                )
                // Assert
                .andExpect(status().isForbidden())
        ;
    }

    @DisplayName("testa relatório de contratos vigentes sem resultados retorna erro")
    @Test
    @Transactional
    void deveLancarErroRelatorioDeContratosVigentesSeNaoHouverResultados() throws Exception {
        // Act
        this.mockMvc
                .perform(
                        get(ENDPOINT_RELATORIO_CONTRATOS_VIGENTES)
                                .with(user(usuarioSindico))
                )
                // Assert
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Erro -  não há dados para o relatório")))
        ;
    }

    @DisplayName("testa relatório de contratos vigentes sem resultados retorna erro")
    @Test
    @Transactional
    void deveLancarErroRelatorioDeContratosVencidosSeNaoHouverResultados() throws Exception {
        // Act
        this.mockMvc
                .perform(
                        get(ENDPOINT_RELATORIO_CONTRATOS_VENCIDOS)
                                .with(user(usuarioSindico))
                )
                // Assert
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Erro -  não há dados para o relatório")))
        ;
    }

    @DisplayName("testa relatório de fornecedores ativos sem resultados retorna erro")
    @Test
    @Transactional
    void deveLancarErroRelatorioDeFornecedoresAtivosSeNaoHouverResultados() throws Exception {
        // Act
        this.mockMvc
                .perform(
                        get(ENDPOINT_RELATORIO_FORNECEDORES_ATIVOS)
                                .with(user(usuarioSindico))
                )
                // Assert
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Erro -  não há dados para o relatório")))
        ;
    }

}
