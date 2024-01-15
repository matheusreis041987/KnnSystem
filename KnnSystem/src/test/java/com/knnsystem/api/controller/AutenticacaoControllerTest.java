package com.knnsystem.api.controller;

import com.knnsystem.api.model.entity.Pessoa;
import com.knnsystem.api.model.entity.Usuario;
import com.knnsystem.api.model.repository.PessoaRepository;
import com.knnsystem.api.model.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class AutenticacaoControllerTest {

    private Usuario usuario;
    private final String ENDPOINT_LOGIN = "/auth/api/login";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp(){
        // Arrange
        Pessoa pessoa = new Pessoa();
        // https://www.4devs.com.br/gerador_de_cpf
        pessoa.setCpf("56214649070");
        pessoa.setNome("Nome da Pessoa");
        pessoa.setEmail("emaildapessoa@email");

        usuario = new Usuario(pessoa);
        usuario.setCargo("Cargo");
        usuario.setPerfil("Perfil");
        usuario.setSenha(passwordEncoder.encode("123456"));
        usuario.setDataNascimento(LocalDate.of(1971,1 ,1));

        usuarioRepository.save(usuario);

    }

    @DisplayName("Testa que consegue logar com senha correta")
    @Test
    void deveLogarComSenhaCorreta() throws Exception {
        // Act
        this.mockMvc.perform(
                post(ENDPOINT_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\"cpf\": \"" + usuario.getCpf() + "\", " +
                                        "\"senha\": \"123456\"}"
                        )
        )
        // Assert
                .andExpect(status().isOk());
    }

}