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
        var morador = testDataBuilder.getMoradorA();
        var proprietario = testDataBuilder.getProprietarioA();
        var apartamento = testDataBuilder.getApartamentoAtivo(morador, proprietario);

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
                .andExpect(jsonPath("$[0].numeroDoApartamento",
                        Matchers.is(apartamento.getNumApt())))
                .andExpect(jsonPath("$[0].bloco",
                        Matchers.is(apartamento.getBlocoApt())))
                .andExpect(jsonPath("$[0].nomeDoProprietario",
                        Matchers.is(proprietario.getNome())))
                .andExpect(jsonPath("$[0].nomeDoMorador",
                        Matchers.is(morador.getNome())))
                .andExpect(jsonPath("$[0].telefoneDoProprietario",
                        Matchers.is(proprietario.getTelefones().stream().findFirst().toString())))
                .andExpect(jsonPath("$[0].telefoneDoMorador",
                        Matchers.is(morador.getTelefones().stream().findFirst().toString())))
                .andExpect(jsonPath("$[0].cpfDoProprietario",
                        Matchers.is(proprietario.getCpf())))
                .andExpect(jsonPath("$[0].cpfDoMorador",
                        Matchers.is(morador.getCpf())))
                .andExpect(jsonPath("$[0].emailDoProprietario",
                        Matchers.is(proprietario.getEmail())))
                .andExpect(jsonPath("$[0].emailDoMorador",
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
        var morador = testDataBuilder.getMoradorA();
        var proprietario = testDataBuilder.getProprietarioA();
        moradorRepository.save(morador);
        proprietarioRepository.save(proprietario);

        var apartamento = testDataBuilder.getApartamentoAtivo(morador, proprietario);
        apartamentoRepository.save(apartamento);

        morador = testDataBuilder.getMoradorB();
        proprietario = testDataBuilder.getProprietarioB();
        apartamento = testDataBuilder.getApartamentoAtivo(morador, proprietario);

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

}
