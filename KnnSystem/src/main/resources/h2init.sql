CREATE SCHEMA IF NOT EXISTS sch_pessoas;
CREATE SCHEMA IF NOT EXISTS sch_contratos;
CREATE SCHEMA IF NOT EXISTS sch_financeiro;


drop table if exists sch_contratos.gestor;
drop table if exists sch_contratos.sindico;
drop table if exists sch_pessoas.apartamento;
drop table if exists sch_pessoas.telefone;
drop table if exists sch_pessoas.secretaria;
drop table if exists sch_pessoas.morador;
drop table if exists sch_pessoas.proprietario;
drop table if exists sch_pessoas.usuario;
drop table if exists sch_pessoas.pessoa;


drop sequence if exists id_pessoas_seq;
drop sequence if exists id_usuarios_seq;
drop sequence if exists id_telefone_seq;
drop sequence if exists id_apartamento_seq;
drop sequence if exists id_proprietario_seq;

create sequence id_apartamento_seq;
create sequence id_pessoas_seq;
create sequence id_usuarios_seq;
create sequence id_telefone_seq;
create sequence id_proprietario_seq;

create table sch_pessoas.pessoa (
	id bigint not null default nextval('id_pessoas_seq'),
	cpf character varying(11) not null unique,
	nome character varying(60) not null,
	email character varying(60) not null,
	status character varying(30) not null,
	constraint pk_pessoa primary key (id)
);

create table sch_pessoas.usuario (
	id bigint not null default nextval('id_usuarios_seq'),
	cpf character varying(11) not null unique,
	nome character varying(60) not null,
	email character varying(60) not null,
	cargo character varying(20)	not null,
	perfil character varying(20)	not null,
	dt_nasc date not null,
	senha character varying(80) not null,
	constraint pk_usuario primary key (id),
	constraint fk_id_usuario foreign key (id)
	references sch_pessoas.pessoa (id)
	on delete cascade
	on update cascade
);

create table sch_pessoas.secretaria (
	id bigint not null,
	constraint pk_secretaria primary key (id),
	constraint fk_id_secretaria foreign key (id)
	references sch_pessoas.usuario (id)
	on delete cascade
	on update cascade
);

create table sch_pessoas.proprietario (
	id bigint not null default nextval('id_proprietario_seq'),
	num_rgi integer not null unique,
	constraint pk_proprietario primary key (id),
	constraint fk_id_proprietario foreign key (id)
	references sch_pessoas.pessoa (id)
	on delete cascade
	on update cascade
);

create table sch_pessoas.morador (
	id bigint not null,
	num_apt integer not null,
	bloco character varying(10) not null,
	constraint pk_morador primary key (id),
	constraint fk_id_morador foreign key (id)
	references sch_pessoas.pessoa (id)
	on delete cascade
	on update cascade
);

create table sch_pessoas.telefone (
	id bigint not null default nextval('id_telefone_seq'),
	numero character varying(15) not null,
	pessoa_id bigint not null,
	constraint pk_telefones primary key (id),
	constraint fk_id_telefones foreign key (pessoa_id)
	references sch_pessoas.pessoa (id)
	on delete cascade
	on update cascade
);



create table sch_contratos.sindico (
	cpf character varying(11) not null,
	nome character varying(60) not null,
	email character varying(60) not null,
	constraint pk_sindico primary key (cpf)

);

create table sch_contratos.gestor (
	cpf character varying(11) not null,
	nome character varying(60) not null,
	email character varying(60) not null,
	constraint pk_gestor primary key (cpf)
);

create table sch_pessoas.apartamento (
	id bigint not null default nextval('id_apartamento_seq'),
	fk_proprietario integer not null,
	fk_morador integer not null,
	metragem numeric (10, 2) not null,
	bloco character varying(10) not null,
	numero integer not null,
	status character varying(30) not null,
	constraint pk_apartamento primary key (id),

	constraint fk_id_apartamento_proprietario foreign key (fk_proprietario)
	references sch_pessoas.proprietario (id)
	on delete cascade
	on update cascade,

	constraint fk_id_apartamento_morador foreign key (fk_morador)
	references sch_pessoas.morador (id)
	on delete cascade
	on update cascade

);