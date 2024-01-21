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
        Telefone telefone = new Telefone();
        telefone.setNumero("21987654321");
        pessoa.adicionaTelefone(telefone);

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

    public Usuario createUsuarioAdministrador(){
        // Ativo
        // Arrange
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1);
        // https://www.4devs.com.br/gerador_de_cpf
        pessoa.setCpf("44890168087");
        pessoa.setNome("Administrador");
        pessoa.setEmail("emaildoadministrador@knnsytem.com");
        pessoa.setStatus(StatusGeral.ATIVO);

        Usuario usuarioAtivo = new Usuario(pessoa);
        usuarioAtivo.setCargo(Cargo.ADMINISTRADOR);
        usuarioAtivo.setSenha(passwordEncoder.encode("1234567C"));
        usuarioAtivo.setDataNascimento(LocalDate.of(1955,1 ,1));

        return usuarioAtivo;
    }

    public Usuario createUsuarioSecretaria(){
        // Ativo
        // Arrange
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1);
        // https://www.4devs.com.br/gerador_de_cpf
        pessoa.setCpf("94302590084");
        pessoa.setNome("Funcionário da secretaria");
        pessoa.setEmail("emaildofuncionario@knnsytem.com");
        pessoa.setStatus(StatusGeral.ATIVO);

        Usuario usuarioAtivo = new Usuario(pessoa);
        usuarioAtivo.setCargo(Cargo.FUNCIONARIO);
        usuarioAtivo.setSenha(passwordEncoder.encode("1234567A"));
        usuarioAtivo.setDataNascimento(LocalDate.of(1989,6 ,1));

        return usuarioAtivo;
    }

    public Usuario createUsuarioSindico(){
        // Ativo
        // Arrange
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1);
        // https://www.4devs.com.br/gerador_de_cpf
        pessoa.setCpf("94142552066");
        pessoa.setNome("Sindico");
        pessoa.setEmail("emaildosindico@knnsytem.com");
        pessoa.setStatus(StatusGeral.ATIVO);

        Usuario usuarioAtivo = new Usuario(pessoa);
        usuarioAtivo.setCargo(Cargo.SINDICO);
        usuarioAtivo.setSenha(passwordEncoder.encode("1234567B"));
        usuarioAtivo.setDataNascimento(LocalDate.of(1969,5 ,1));

        return usuarioAtivo;
    }

    public Apartamento getApartamentoAtivo( Morador morador, Proprietario proprietario){
        Apartamento apartamento = new Apartamento();
        apartamento.setMorador(morador);
        apartamento.setProprietario(proprietario);
        apartamento.setNumApt(morador.getNumApt());
        apartamento.setBlocoApt(morador.getBloco());
        apartamento.setStatusApt(StatusGeral.ATIVO);

        return apartamento;
    }

    public Morador getMoradorA(){
        Morador morador = new Morador();
        morador.setCpf("50729301060");
        morador.setNome("Morador do apartamento A");
        morador.setEmail("emaildomoradordoa@knnsystem.com.br");
        morador.setBloco("Bloco X");
        morador.setNumApt(101);

        return morador;
    }

    public Proprietario getProprietarioA(){
        Proprietario proprietario = new Proprietario();
        proprietario.setCpf("04578520030");
        proprietario.setNome("Proprietário do apartamento A");
        proprietario.setEmail("emaildoproprietariodoa@knnsystem.com.br");
        proprietario.setRegistroImovel(123);

        return proprietario;

    }

    public Morador getMoradorB(){
        Morador morador = new Morador();
        morador.setCpf("51105530094");
        morador.setNome("Morador do apartamento B");
        morador.setEmail("emaildomoradordoa@knnsystem.com.br");
        morador.setBloco("Bloco Y");
        morador.setNumApt(102);

        return morador;
    }

    public Proprietario getProprietarioB(){
        Proprietario proprietario = new Proprietario();
        proprietario.setCpf("30429734093");
        proprietario.setNome("Proprietário do apartamento B");
        proprietario.setEmail("emaildoproprietariodob@knnsystem.com.br");
        proprietario.setRegistroImovel(456);

        return proprietario;

    }

    public Fornecedor createFornecedorA(){

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setRazaoSocial("Razão Social EIRELI");
        fornecedor.setCnpj("00000000000111");
        fornecedor.setResponsavel(createResponsavelA());
        fornecedor.setEmailCorporativo("razaosocialeireli@rzeireli.com.br");
        fornecedor.setNaturezaServico("Natureza do serviço do Fornecedor A");
        fornecedor.setEnderecoCompleto("Endereço do Fornecedor A");
        fornecedor.setDomicilioBancario(createDomicilioBancarioA());
        fornecedor.setStatusFornecedor(StatusGeral.ATIVO);

        return fornecedor;
    }

    public DomicilioBancario createDomicilioBancarioA(){
        DomicilioBancario domicilioBancario = new DomicilioBancario();
        domicilioBancario.setAgencia("1234");
        domicilioBancario.setContaCorrente("4321");
        domicilioBancario.setBanco("001");
        domicilioBancario.setPix("407c1a87-d76c-4ae1-945b-0d1e91a68a93");
        domicilioBancario.setStatusDomicilio(StatusGeral.ATIVO);
        return domicilioBancario;
    }

    public Responsavel createResponsavelA(){
        Responsavel responsavel = new Responsavel();
        responsavel.setCpf("11111111111");
        responsavel.setEmail("responsavela@knnsystem.com.br");
        responsavel.setNome("Responsável A do Fornecedor A");

        return responsavel;
    }

    public Fornecedor createFornecedorB(){

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setRazaoSocial("Razão Social S.A;");
        fornecedor.setCnpj("00000000000122");
        fornecedor.setResponsavel(createResponsavelB());
        fornecedor.setEmailCorporativo("razaosocialsa@rzsa.com.br");
        fornecedor.setNaturezaServico("Natureza do serviço do Fornecedor B");
        fornecedor.setEnderecoCompleto("Endereço do Fornecedor B");
        fornecedor.setDomicilioBancario(createDomicilioBancarioB());
        fornecedor.setStatusFornecedor(StatusGeral.ATIVO);

        return fornecedor;
    }

    public DomicilioBancario createDomicilioBancarioB(){
        DomicilioBancario domicilioBancario = new DomicilioBancario();
        domicilioBancario.setAgencia("1234");
        domicilioBancario.setContaCorrente("4321");
        domicilioBancario.setBanco("001");
        domicilioBancario.setPix("407c1a87-d76c-4ae1-945b-0d1e91a68a93");
        domicilioBancario.setStatusDomicilio(StatusGeral.ATIVO);
        return domicilioBancario;
    }

    public Responsavel createResponsavelB(){
        Responsavel responsavel = new Responsavel();
        responsavel.setCpf("11111111111");
        responsavel.setEmail("responsavela@knnsystem.com.br");
        responsavel.setNome("Responsável A do Fornecedor A");

        return responsavel;
    }

}
