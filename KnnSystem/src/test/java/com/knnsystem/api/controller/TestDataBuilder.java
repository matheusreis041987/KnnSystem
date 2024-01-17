package com.knnsystem.api.controller;

import com.knnsystem.api.model.entity.*;
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
        pessoa.setEmail("emaildapessoa@knnsytem.com");
        pessoa.setStatus(StatusGeral.ATIVO);

        Usuario usuarioAtivo = new Usuario(pessoa);
        usuarioAtivo.setCargo(Cargo.SINDICO);
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
        pessoa.setEmail("emaildapessoa2@knnsytem.com");
        pessoa.setStatus(StatusGeral.INATIVO);

        Usuario usuarioInativo = new Usuario(pessoa);
        usuarioInativo.setCargo(Cargo.FUNCIONARIO);
        usuarioInativo.setSenha(passwordEncoder.encode("1234567"));
        usuarioInativo.setDataNascimento(LocalDate.of(1980,12 ,13));

        return usuarioInativo;

    }

    public Usuario createUsuarioNovo(){
        // Ativo
        // Arrange
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1);
        // https://www.4devs.com.br/gerador_de_cpf
        pessoa.setCpf("40586756086");
        pessoa.setNome("Novo usuário");
        pessoa.setEmail("novousuario@knnsytem.com");
        pessoa.setStatus(StatusGeral.ATIVO);
        Telefone telefone = new Telefone();
        telefone.setNumero("21987654321");
        pessoa.adicionaTelefone(telefone);

        Usuario usuario = new Usuario(pessoa);
        usuario.setCargo(Cargo.FUNCIONARIO);
        usuario.setDataNascimento(LocalDate.of(1991,1 ,1));

        return usuario;
    }

}
