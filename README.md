Knn System back-end
=

![status_desenvolvimento](https://img.shields.io/static/v1?label=Status&message=Em%20Desenvolvimento&color=yellow&style=for-the-badge)
![Badge Java](https://img.shields.io/static/v1?label=Java&message=17&color=orange&style=for-the-badge&logo=java)

![framework_back](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white)

Repositório do back-end para o sistema KnnSystem, desenvolvido como trabalho final pós graduação análise e projeto de sistemas - PUC-RJ.

Utilizaremos o *framework* Spring Boot, com Postgres como SGBD.

# Como executar

É necessário executar a aplicação em ambiente com Postgres.

Executar os comandos abaixos na cli do psql previamente à execução da aplicação:

```
# substituir {username} pelo nome de usuário da aplicação no bd local
CREATE DATABASE "db_KnnSystem" IF NOT EXISTS;
\c db_KnnSystem;
CREATE SCHEMA IF NOT EXISTS sch_pessoas AUTHORIZATION {username};
CREATE SCHEMA IF NOT EXISTS sch_contratos AUTHORIZATION {username};
CREATE SCHEMA IF NOT EXISTS sch_financeiro AUTHORIZATION {username};
```

Deve-se também configurar as variáveis de ambiente abaixo na IDE:

* `DB_USER`
* `DB_PASSWORD`
* `JWT_SECRET`
* `JWT_ISSUER`

# 🗓️ Resumo Desenvolvimento

* Como metodologia de trabalho, usamos variação simplificado do [git flow](https://www.atlassian.com/br/git/tutorials/comparing-workflows/gitflow-workflow), com a *branch* *develop* sendo utilizada como ramificação a partir da qual *branchs* de *features* são criadas e para a qual as *pull requests* são direcionadas (a ramificação *main* só recebe *merges* a partir da *develop*);
* Utilizou-se a funcionalidade do [Github Actions](https://docs.github.com/pt/actions) para execução automatizada de testes unitários e de integração para capturar antecipadamente eventuais problemas com o código;
* Utilizou-se o padrão [Test Data Builder](https://robsoncastilho.com.br/2020/03/27/test-data-builders-voce-esta-usando-corretamente/) para criação da *massa* de testes de integração/unidade;
* Utilizou-se [Spring Security](https://docs.spring.io/spring-security/reference/index.html) para segurança e controle de acesso, além de [JWT](https://github.com/auth0/java-jwt) para autorização e autenticação; 
