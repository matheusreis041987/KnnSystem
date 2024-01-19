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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ApartamentoControllerTest {

    private Apartamento apartamentoA;

    private Apartamento apartamentoB;

    private Usuario usuarioAdministrador;

    private Usuario usuarioSecretaria;

    private Usuario usuarioSindico;

    private final String ENDPOINT_CADASTRO = "/apartamento/api/cadastra";

    private final String ENDPOINT_CONSULTA = "/apartamento/api/consulta";

    private final String ENDPOINT_INATIVA = "/apartamento/api/inativa";

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
        // Primeiro Apartamento
        var morador = testDataBuilder.getMoradorA();
        var proprietario = testDataBuilder.getProprietarioA();
        morador = moradorRepository.save(morador);
        proprietario = proprietarioRepository.save(proprietario);
        apartamentoA = testDataBuilder.getApartamentoAtivo(morador, proprietario);

        // Segundo Apartamento
        morador = testDataBuilder.getMoradorB();
        proprietario = testDataBuilder.getProprietarioB();
        morador = moradorRepository.save(morador);
        proprietario = proprietarioRepository.save(proprietario);
        apartamentoB = testDataBuilder.getApartamentoAtivo(morador, proprietario);

        usuarioAdministrador = testDataBuilder.createUsuarioAdministrador();
        usuarioSecretaria = testDataBuilder.createUsuarioSecretaria();
        usuarioSindico = testDataBuilder.createUsuarioSindico();
    }

    @AfterEach
    void tearDown() {

    }

    @DisplayName("Testa que não pode cadastrar novo apartamento com número e bloco já utilizado")
    @Test
    @Transactional
    void naoDeveCadastrarApartamentoPelaSegundaVez() throws Exception {
        // Arrange
        apartamentoA = apartamentoRepository.save(apartamentoA);
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioAdministrador))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"numeroDoApartamento\": " + apartamentoA.getNumApt() + ", " +
                                                "\"bloco\": \"" + apartamentoA.getBlocoApt() + "\", " +
                                                "\"nomeDoProprietario\": \"" + apartamentoA.getProprietario().getNome() + "\", " +
                                                "\"telefoneDoProprietario\": \"" + apartamentoA.getProprietario().getTelefones().stream().findFirst() + "\", " +
                                                "\"cpfDoProprietario\": \"" + apartamentoA.getProprietario().getCpf() + "\", " +
                                                "\"emailDoProprietario\": \"" + apartamentoA.getProprietario().getEmail() + "\", " +
                                                "\"nomeDoMorador\": \"" + apartamentoA.getMorador().getNome() + "\", " +
                                                "\"telefoneDoMorador\": \"" + apartamentoA.getMorador().getTelefones().stream().findFirst() + "\", " +
                                                "\"cpfDoMorador\": \"" + apartamentoA.getMorador().getCpf() + "\", " +
                                                "\"emailDoMorador\": \"" + apartamentoA.getMorador().getEmail() + "\", " +
                                                "\"telefoneDoProprietario\": \"" + apartamentoA.getProprietario().getTelefones().stream().findFirst() + "\", " +
                                                "\"metragemDoImovel\": " + apartamentoA.getMetragem() + "}"
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
                                        "{\"numeroDoApartamento\": " + apartamentoA.getNumApt() + ", " +
                                                "\"bloco\": \"" + apartamentoA.getBlocoApt() + "\", " +
                                                "\"nomeDoProprietario\": \"" + apartamentoA.getProprietario().getNome() + "\", " +
                                                "\"telefoneDoProprietario\": \"" + apartamentoA.getProprietario().getTelefones().stream().findFirst() + "\", " +
                                                "\"cpfDoProprietario\": \"123456789101\", " +
                                                "\"emailDoProprietario\": \"" + apartamentoA.getProprietario().getEmail() + "\", " +
                                                "\"nomeDoMorador\": \"" + apartamentoA.getMorador().getNome() + "\", " +
                                                "\"cpfDoMorador\": \"" + apartamentoA.getMorador().getCpf() + "\", " +
                                                "\"emailDoMorador\": \"" + apartamentoA.getMorador().getEmail() + "\", " +
                                                "\"telefoneDoMorador\": \"" + apartamentoA.getMorador().getTelefones().stream().findFirst() + "\", " +
                                                "\"metragemDoImovel\": " + apartamentoA.getMetragem() + "}"
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
                                        "{\"numeroDoApartamento\": " + apartamentoA.getNumApt() + ", " +
                                                "\"bloco\": \"" + apartamentoA.getBlocoApt() + "\", " +
                                                "\"nomeDoProprietario\": \"" + apartamentoA.getProprietario().getNome() + "\", " +
                                                "\"telefoneDoProprietario\": \"" + apartamentoA.getProprietario().getTelefones().stream().findFirst() + "\", " +
                                                "\"cpfDoProprietario\": \"" + apartamentoA.getProprietario().getCpf() + "\", " +
                                                "\"emailDoProprietario\": \"" + apartamentoA.getProprietario().getEmail() + "\", " +
                                                "\"nomeDoMorador\": \"" + apartamentoA.getMorador().getNome() + "\", " +
                                                "\"cpfDoMorador\": \"" + apartamentoA.getMorador().getCpf() + "\", " +
                                                "\"emailDoMorador\": \"" + apartamentoA.getMorador().getEmail() + "\", " +
                                                "\"telefoneDoProprietario\": \"" + apartamentoA.getProprietario().getTelefones().stream().findFirst() + "\", " +
                                                "\"metragemDoImovel\": " + apartamentoA.getMetragem() + "}"
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
                                        "{\"numeroDoApartamento\": " + apartamentoA.getNumApt() + ", " +
                                                "\"bloco\": \"" + apartamentoA.getBlocoApt() + "\", " +
                                                "\"nomeDoProprietario\": \"" + apartamentoA.getProprietario().getNome() + "\", " +
                                                "\"telefoneDoProprietario\": \"" + apartamentoA.getProprietario().getTelefones().stream().findFirst() + "\", " +
                                                "\"cpfDoProprietario\": \"" + apartamentoA.getProprietario().getCpf() + "\", " +
                                                "\"emailDoProprietario\": \"" + apartamentoA.getProprietario().getEmail() + "\", " +
                                                "\"nomeDoMorador\": \"" + apartamentoA.getMorador().getNome() + "\", " +
                                                "\"telefoneDoMorador\": \"" + apartamentoA.getMorador().getTelefones().stream().findFirst() + "\", " +
                                                "\"cpfDoMorador\": \"" + apartamentoA.getMorador().getCpf() + "\", " +
                                                "\"emailDoMorador\": \"" + apartamentoA.getMorador().getEmail() + "\", " +
                                                "\"telefoneDoProprietario\": \"" + apartamentoA.getProprietario().getTelefones().stream().findFirst() + "\", " +
                                                "\"metragemDoImovel\": " + apartamentoA.getMetragem() + "}"
                                )
                )
                // Assert
                .andExpect(status().isCreated());
    }

    @DisplayName("Testa consulta para repositório de apartamentos vazio")
    @Test
    @Transactional
    void deveRetornarErroSeNaoHouverApartamentos() throws Exception {
        // Act
        this.mockMvc.perform(
                get(ENDPOINT_CONSULTA)
                        .with(user(usuarioAdministrador)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Não há um apartamento cadastrado para os dados informados")));

    }

    @DisplayName("Testa consulta quando não encontra número do apartamento")
    @Test
    @Transactional
    void deveRetornarErroSeNaoHouverApartamentosParaNumeroInformado() throws Exception {
        // Arrange
        apartamentoRepository.save(apartamentoA);

        // Act
        this.mockMvc.perform(
                        get(ENDPOINT_CONSULTA)
                                .param("numero", Integer.toString(apartamentoB.getNumApt()))
                                .with(user(usuarioAdministrador)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Não há um apartamento cadastrado para os dados informados")));

    }

    @DisplayName("Testa consulta quando não encontra bloco do apartamento")
    @Test
    @Transactional
    void deveRetornarErroSeNaoHouverApartamentosParaBlocoInformado() throws Exception {
        // Arrange
        apartamentoRepository.save(apartamentoA);

        // Act
        this.mockMvc.perform(
                        get(ENDPOINT_CONSULTA)
                                .param("bloco", apartamentoB.getBlocoApt())
                                .with(user(usuarioAdministrador)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Não há um apartamento cadastrado para os dados informados")));

    }

    @DisplayName("Testa consulta quando encontra tanto número quanto bloco")
    @Test
    @Transactional
    void deveRetornarApartamentosQuePossuamNumeroOuBlocoInformado() throws Exception {
        // Arrange
        apartamentoRepository.save(apartamentoA);
        apartamentoRepository.save(apartamentoB);

        // Act
        this.mockMvc.perform(
                        get(ENDPOINT_CONSULTA)
                                .param("numero", Integer.toString(apartamentoB.getNumApt()))
                                .param("bloco", apartamentoA.getBlocoApt())
                                .with(user(usuarioAdministrador)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",
                        Matchers.hasSize(2)));

    }

    @DisplayName("Testa inativação de apartamento dá erro quando não encontra")
    @Test
    @Transactional
    void deveRetornarErroAoTentarInativarApartamentosNaoEncontrado() throws Exception {

        // Act
        this.mockMvc.perform(
                        put(ENDPOINT_INATIVA)
                                .param("numero", Integer.toString(apartamentoA.getNumApt()))
                                .param("bloco", apartamentoA.getBlocoApt())
                                .with(user(usuarioAdministrador)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Não há um apartamento cadastrado para os dados informados")));

    }

    @DisplayName("Testa inativação de apartamento com sucesso")
    @Test
    @Transactional
    void deveRetornarDadosDeApartamentoAoInativarComSucesso() throws Exception {
        // Arrange
        apartamentoRepository.save(apartamentoA);

        // Act
        this.mockMvc.perform(
                        put(ENDPOINT_INATIVA)
                                .param("numero", Integer.toString(apartamentoA.getNumApt()))
                                .param("bloco", apartamentoA.getBlocoApt())
                                .with(user(usuarioAdministrador)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroDoApartamento",
                        Matchers.is(apartamentoA.getNumApt())))
                .andExpect(jsonPath("$.bloco",
                        Matchers.is(apartamentoA.getBlocoApt())))
        ;

    }

    @DisplayName("Testa que funcionário secretaria não pode cadastrar novo usuário")
    @Test
    @Transactional
    void naoDeveCadastrarApartamentoSeForFuncionarioDaSecretaria() throws Exception {
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"numeroDoApartamento\": " + apartamentoA.getNumApt() + ", " +
                                                "\"bloco\": \"" + apartamentoA.getBlocoApt() + "\", " +
                                                "\"nomeDoProprietario\": \"" + apartamentoA.getProprietario().getNome() + "\", " +
                                                "\"telefoneDoProprietario\": \"" + apartamentoA.getProprietario().getTelefones().stream().findFirst() + "\", " +
                                                "\"cpfDoProprietario\": \"" + apartamentoA.getProprietario().getCpf() + "\", " +
                                                "\"emailDoProprietario\": \"" + apartamentoA.getProprietario().getEmail() + "\", " +
                                                "\"nomeDoMorador\": \"" + apartamentoA.getMorador().getNome() + "\", " +
                                                "\"telefoneDoMorador\": \"" + apartamentoA.getMorador().getTelefones().stream().findFirst() + "\", " +
                                                "\"cpfDoMorador\": \"" + apartamentoA.getMorador().getCpf() + "\", " +
                                                "\"emailDoMorador\": \"" + apartamentoA.getMorador().getEmail() + "\", " +
                                                "\"telefoneDoProprietario\": \"" + apartamentoA.getProprietario().getTelefones().stream().findFirst() + "\", " +
                                                "\"metragemDoImovel\": " + apartamentoA.getMetragem() + "}"
                                )
                )
                // Assert
                .andExpect(status().isForbidden())
        ;
    }

    @DisplayName("Testa que perfil sindico não pode cadastrar novo usuário")
    @Test
    @Transactional
    void naoDeveCadastrarApartamentoSePerfilDeSindico() throws Exception {
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioSindico))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"numeroDoApartamento\": " + apartamentoA.getNumApt() + ", " +
                                                "\"bloco\": \"" + apartamentoA.getBlocoApt() + "\", " +
                                                "\"nomeDoProprietario\": \"" + apartamentoA.getProprietario().getNome() + "\", " +
                                                "\"telefoneDoProprietario\": \"" + apartamentoA.getProprietario().getTelefones().stream().findFirst() + "\", " +
                                                "\"cpfDoProprietario\": \"" + apartamentoA.getProprietario().getCpf() + "\", " +
                                                "\"emailDoProprietario\": \"" + apartamentoA.getProprietario().getEmail() + "\", " +
                                                "\"nomeDoMorador\": \"" + apartamentoA.getMorador().getNome() + "\", " +
                                                "\"telefoneDoMorador\": \"" + apartamentoA.getMorador().getTelefones().stream().findFirst() + "\", " +
                                                "\"cpfDoMorador\": \"" + apartamentoA.getMorador().getCpf() + "\", " +
                                                "\"emailDoMorador\": \"" + apartamentoA.getMorador().getEmail() + "\", " +
                                                "\"telefoneDoProprietario\": \"" + apartamentoA.getProprietario().getTelefones().stream().findFirst() + "\", " +
                                                "\"metragemDoImovel\": " + apartamentoA.getMetragem() + "}"
                                )
                )
                // Assert
                .andExpect(status().isForbidden())
        ;
    }

}