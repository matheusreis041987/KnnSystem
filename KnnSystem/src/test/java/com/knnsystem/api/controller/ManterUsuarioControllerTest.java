package com.knnsystem.api.controller;


import com.knnsystem.api.model.entity.Usuario;
import com.knnsystem.api.model.repository.PessoaRepository;
import com.knnsystem.api.model.repository.UsuarioRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ManterUsuarioControllerTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Usuario usuarioAtivo;

    private Usuario usuarioInativo;

    private Usuario usuarioAdministrador;

    private Usuario usuarioSecretaria;

    private final String ENDPOINT_CONSULTA_BASE = "/usuario/api";

    private final String ENDPOINT_CADASTRO = "/usuario/api/cadastra";

    private final String ENDPOINT_ATIVAR = "/usuario/api/ativa";

    private final String ENDPOINT_INATIVAR = "/usuario/api/inativa";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TestDataBuilder testDataBuilder;

    @BeforeEach
    void setUp(){
        usuarioAtivo = testDataBuilder.createUsuarioAtivo();
        usuarioRepository.save(usuarioAtivo);

        usuarioAdministrador = testDataBuilder.createUsuarioAdministrador();
        usuarioSecretaria = testDataBuilder.createUsuarioSecretaria();
    }

    @AfterEach
    void tearDown(){
    }

    @DisplayName("Testa que não pode cadastrar novo usuário com cpf já utilizado")
    @Test
    void naoDeveCadastrarCpfPelaSegundaVez() throws Exception {
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioAdministrador))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"cpf\": \"" + usuarioAtivo.getCpf() + "\", " +
                                                "\"nome\": \"" + usuarioAtivo.getNome() + "\", " +
                                                "\"email\": \"" + usuarioAtivo.getEmail() + "\", " +
                                                "\"dataNascimento\": \"" + usuarioAtivo.getDataNascimento() + "\", " +
                                                "\"cargo\": \"" + usuarioAtivo.getCargo() + "\", " +
                                                "\"senha\": \"1234567\"}"
                                )
                )
                // Assert
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("CPF já cadastrado")))
        ;
    }


    @DisplayName("Testa que não funcionário secretaria não pode cadastrar novo usuário")
    @Test
    void naoDeveCadastrarUsuarioSeForFuncionarioDaSecretaria() throws Exception {
        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"cpf\": \"" + usuarioAtivo.getCpf() + "\", " +
                                                "\"nome\": \"" + usuarioAtivo.getNome() + "\", " +
                                                "\"email\": \"" + usuarioAtivo.getEmail() + "\", " +
                                                "\"dataNascimento\": \"" + usuarioAtivo.getDataNascimento() + "\", " +
                                                "\"cargo\": \"" + usuarioAtivo.getCargo() + "\", " +
                                                "\"senha\": \"1234567\"}"
                                )
                )
                // Assert
                .andExpect(status().isForbidden())
        ;
    }

    @DisplayName("Testa cadastro de usuário com dados válidos")
    @Test
    void deveCadastrarUsuarioComDadosValidos() throws Exception {
        // Arrange
        Usuario usuarioNovo = testDataBuilder.createUsuarioNovo();

        // Act
        this.mockMvc.perform(
                        post(ENDPOINT_CADASTRO)
                                .with(user(usuarioAdministrador))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"cpf\": \"" + usuarioNovo.getCpf() + "\", " +
                                                "\"nome\": \"" + usuarioNovo.getNome() + "\", " +
                                                "\"email\": \"" + usuarioNovo.getEmail() + "\", " +
                                                "\"dataNascimento\": \"" + usuarioNovo.getDataNascimento() + "\", " +
                                                "\"cargo\": \"" + usuarioNovo.getCargo() + "\", " +
                                                "\"senha\": \"12A45678\"}"
                                )
                )
                // Assert
                .andExpect(status().isCreated());
    }

    @DisplayName("Testa consulta de usuário por CPF retorna erro se não encontra")
    @Test
    void testDeveLancarErroSeNaoEncontrarUsuarioPorCPF() throws Exception {
        // Arrange
        Usuario usuarioNovo = testDataBuilder.createUsuarioNovo();

        // Act
        this.mockMvc
                .perform(
                        get(ENDPOINT_CONSULTA_BASE + "/" + usuarioNovo.getCpf())
                                .with(user(usuarioAdministrador))
                )
                // Assert
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("Erro - não há usuário cadastrado para esse CPF")))
        ;

    }

    @DisplayName("Testa funcionário de secretaria não consulta por CPF")
    @Test
    void testNaoDevePermitirFuncionarioSecretariaEncontrarUsuarioPorCPF() throws Exception {
        // Act
        this.mockMvc
                .perform(
                        get(ENDPOINT_CONSULTA_BASE + "/" + usuarioAtivo.getCpf())
                                .with(user(usuarioSecretaria))
                )
                // Assert
                .andExpect(status().isForbidden())
        ;

    }

    @DisplayName("Testa consulta de usuário por CPF existente o retorna")
    @Test
    void testDeveRetornarUsuarioCorrespondenteACPFNaConsulta() throws Exception {
        // Act
        this.mockMvc
                .perform(
                        get(ENDPOINT_CONSULTA_BASE + "/" + usuarioAtivo.getCpf())
                                .with(user(usuarioAdministrador))
                )
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome",
                        Matchers.is(usuarioAtivo.getNome())))
                .andExpect(jsonPath("$.cpf",
                        Matchers.is(usuarioAtivo.getCpf())))
                .andExpect(jsonPath("$.email",
                        Matchers.is(usuarioAtivo.getEmail())))
                .andExpect(jsonPath("$.dataNascimento",
                        Matchers.is(usuarioAtivo.getDataNascimento().toString())))
                .andExpect(jsonPath("$.cargo",
                        Matchers.is(usuarioAtivo.getCargo().toString())))
                .andExpect(jsonPath("$.status",
                        Matchers.is(usuarioAtivo.getPessoa().getStatus().toString())))
        ;



    }

    @DisplayName("Testa atualização de usuário por CPF retorna erro se não encontra")
    @Test
    void testDeveLancarErroSeNaoEncontrarUsuarioPorCPFNaAtualizacao() throws Exception {
        // Arrange
        Usuario usuarioNovo = testDataBuilder.createUsuarioNovo();

        // Act
        this.mockMvc
                .perform(
                        put(ENDPOINT_CONSULTA_BASE + "/" + usuarioNovo.getCpf())
                                .with(user(usuarioAdministrador))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"cpf\": \"" + usuarioNovo.getCpf() + "\", " +
                                                "\"nome\": \"" + usuarioNovo.getNome() + "\", " +
                                                "\"email\": \"" + usuarioNovo.getEmail() + "\", " +
                                                "\"telefone\": \"" + usuarioNovo.getPessoa().getTelefones().stream().findFirst().get() + "\", " +
                                                "\"dataNascimento\": \"" + usuarioNovo.getDataNascimento() + "\", " +
                                                "\"cargo\": \"" + usuarioNovo.getCargo() + "\", " +
                                                "\"senha\": \"1234567\"}"
                                )
                )
                // Assert
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem",
                        Matchers.is("CPF inválido")))
        ;

    }

    @DisplayName("Testa atualização de usuário por CPF não é permitida a secretaria")
    @Test
    void testNaoDeveAtualizarUsuarioSeFuncionarioSecretaria() throws Exception {
        // Act
        this.mockMvc
                .perform(
                        put(ENDPOINT_CONSULTA_BASE + "/" + usuarioAtivo.getCpf())
                                .with(user(usuarioSecretaria))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"cpf\": \"" + usuarioAtivo.getCpf() + "\", " +
                                                "\"nome\": \"" + usuarioAtivo.getNome() + "\", " +
                                                "\"email\": \"" + usuarioAtivo.getEmail() + "\", " +
                                                "\"telefone\": \"" + usuarioAtivo.getPessoa().getTelefones().stream().findFirst().get() + "\", " +
                                                "\"dataNascimento\": \"" + usuarioAtivo.getDataNascimento() + "\", " +
                                                "\"cargo\": \"" + usuarioAtivo.getCargo() + "\", " +
                                                "\"senha\": \"1234567\"}"
                                )
                )
                // Assert
                .andExpect(status().isForbidden())
        ;

    }

    @DisplayName("Testa ativação de usuário inativo")
    @Test
    void deveAtivarUsuarioInativo() throws Exception {

        // Arrange
        usuarioInativo = testDataBuilder.createUsuarioInativo();
        usuarioRepository.save(usuarioInativo);

        // Act
        this.mockMvc.perform(
                        put(ENDPOINT_ATIVAR + "/" + usuarioInativo.getCpf())
                                .with(user(usuarioAdministrador))
                )
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status",
                        Matchers.is("ATIVO")));
    }

    @DisplayName("Testa perfil de secretaria não pode ativar usuário inativo")
    @Test
    void naoDeveAtivarUsuarioInativoSeSecretaria() throws Exception {

        // Arrange
        usuarioInativo = testDataBuilder.createUsuarioInativo();
        usuarioRepository.save(usuarioInativo);

        // Act
        this.mockMvc.perform(
                        put(ENDPOINT_ATIVAR + "/" + usuarioInativo.getCpf())
                                .with(user(usuarioSecretaria))
                )
                // Assert
                .andExpect(status().isForbidden());
    }

    @DisplayName("Testa inativação de usuário ativo")
    @Test
    void deveInativarUsuarioAtivo() throws Exception {
        // Arrange
        usuarioInativo = testDataBuilder.createUsuarioInativo();
        usuarioRepository.save(usuarioInativo);

        // Act
        this.mockMvc.perform(
                        put(ENDPOINT_INATIVAR + "/" + usuarioInativo.getCpf())
                                .with(user(usuarioAdministrador))
                )
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status",
                        Matchers.is("INATIVO")));
    }

    @DisplayName("Testa não deve inativar usuário ativo se funcionário da secretaria")
    @Test
    void naoDeveInativarUsuarioAtivoSeSecretaria() throws Exception {
        // Arrange
        usuarioInativo = testDataBuilder.createUsuarioInativo();
        usuarioRepository.save(usuarioInativo);

        // Act
        this.mockMvc.perform(
                        put(ENDPOINT_INATIVAR + "/" + usuarioInativo.getCpf())
                                .with(user(usuarioSecretaria))
                )
                // Assert
                .andExpect(status().isForbidden());
    }

}