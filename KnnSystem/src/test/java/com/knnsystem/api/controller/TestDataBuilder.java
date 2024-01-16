package com.knnsystem.api.controller;

import com.knnsystem.api.model.entity.Pessoa;
import com.knnsystem.api.model.entity.StatusGeral;
import com.knnsystem.api.model.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TestDataBuilder {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario createUsuarioAtivo(){
        // Ativo
        // Arrange
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1);
        // https://www.4devs.com.br/gerador_de_cpf
        pessoa.setCpf("56214649070");
        pessoa.setNome("Nome da Pessoa");
        pessoa.setEmail("emaildapessoa@email");
        pessoa.setStatus(StatusGeral.ATIVO);

        Usuario usuarioAtivo = new Usuario(pessoa);
        usuarioAtivo.setCargo("Cargo");
        usuarioAtivo.setPerfil("Perfil");
        usuarioAtivo.setSenha(passwordEncoder.encode("123456"));
        usuarioAtivo.setDataNascimento(LocalDate.of(1971,1 ,1));

        return usuarioAtivo;
    }

    public Usuario createUsuarioInativo(){

        // Inativo
        // Arrange
        Pessoa pessoa = new Pessoa();
        pessoa.setId(2);
        // https://www.4devs.com.br/gerador_de_cpf
        pessoa.setCpf("77309636040");
        pessoa.setNome("Nome da Pessoa 2");
        pessoa.setEmail("emaildapessoa2@email");
        pessoa.setStatus(StatusGeral.INATIVO);

        Usuario usuarioInativo = new Usuario(pessoa);
        usuarioInativo.setCargo("Cargo 2");
        usuarioInativo.setPerfil("Perfil 2");
        usuarioInativo.setSenha(passwordEncoder.encode("1234567"));
        usuarioInativo.setDataNascimento(LocalDate.of(1980,12 ,13));

        return usuarioInativo;

    }

}
