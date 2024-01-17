CREATE DATABASE "db_KnnSystem"
    WITH
    OWNER = bernardo
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;


CREATE SCHEMA IF NOT EXISTS sch_pessoas
    AUTHORIZATION postgres;

CREATE SCHEMA IF NOT EXISTS sch_contratos
    AUTHORIZATION postgres;

CREATE SCHEMA IF NOT EXISTS sch_financeiro
    AUTHORIZATION postgres;


drop table if exists sch_pessoas.pessoa

create sequence id_pessoas_seq;

create table sch_pessoas.pessoa (
	id bigint not null default nextval('id_pessoas_seq'),
	cpf character varying(11) not null unique,
	nome character varying(60) not null,
	email character varying(60) not null,
	status character varying(30) not null,
	constraint pk_pessoa primary key (id)
);


drop table if exists sch_pessoas.telefone

create sequence id_telefone_seq;

create table sch_pessoas.telefone (
	id bigint not null default nextval('id_telefone_seq'),
	numero character varying(15) not null,
	pessoa_id bigint not null,
	constraint pk_telefones primary key (id),
	constraint fk_id_telefones foreign key (pessoa_id)
	references sch_pessoas.pessoa (id) match simple
	on delete cascade
	on update cascade
);

drop table if exists sch_pessoas.proprietario

create sequence id_proprietario_seq;

create table sch_pessoas.proprietario (
	id bigint not null default nextval('id_proprietario_seq'),
	num_rgi integer not null unique,
	constraint pk_proprietario primary key (id),
	constraint fk_id_proprietario foreign key (id)
	references sch_pessoas.pessoa (id) match simple
	on delete cascade
	on update cascade
);


drop table if exists sch_pessoas.morador

create sequence id_morador_seq;

create table sch_pessoas.morador (
	id bigint not null default nextval('id_proprietario_seq'),
	num_apt integer not null,
	bloco character varying(10) not null,
	constraint pk_morador primary key (id),
	constraint fk_id_morador foreign key (id)
	references sch_pessoas.pessoa (id) match simple
	on delete cascade
	on update cascade
);


drop table if exists sch_pessoas.usuario

create sequence id_usuario_seq;

create table sch_pessoas.usuario (
	id bigint not null default nextval('id_usuario_seq'),
	cargo character varying(20)	not null,
	nome character varying(60) not null,
	cpf character varying(11) not null unique,
	email character varying(60) not null,
	perfil character varying(20)	not null,
	dt_nasc date not null,
	senha character varying(255) not null,
	constraint pk_usuario primary key (id),
	constraint fk_id_usuario foreign key (id)
	references sch_pessoas.pessoa (id) match simple
	on delete cascade
	on update cascade
);


drop table if exists sch_pessoas.apartamento

create sequence id_apartamento_seq;

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
	references sch_pessoas.proprietario (id) match simple
	on delete cascade
	on update cascade,

	constraint fk_id_apartamento_morador foreign key (fk_morador)
	references sch_pessoas.morador (id) match simple
	on delete cascade
	on update cascade

);


drop table if exists sch_financeiro.boleto

create sequence id_boleto_seq;

create table sch_financeiro.boleto (
	id bigint not null default nextval('id_boleto_seq'),
	fk_morador integer not null,
	fk_usuario integer not null,
	data_pagamento timestamp not null,
	vlr_pagamento numeric(15,2) not null check(valor_multa >=0),
	eh_segunda_via boolean,
	status character varying(30) not null,
	pct_multa character varying(10),
	valor_multa numeric(15,2) check(valor_multa >=0),
	valor_total numeric(15,2) not null check(valor_multa >=0),
	cod_barra character varying(40),
	competencia character varying(40) not null,
	constraint pk_boleto primary key (id),

	constraint fk_id_boleto_morador foreign key (fk_morador)
	references sch_pessoas.morador (id) match simple
	on delete cascade
	on update cascade,

	constraint fk_id_boleto_usuario foreign key (fk_usuario)
	references sch_pessoas.usuario (id) match simple
	on delete cascade
	on update cascade

);


drop table if exists sch_contratos.gestor

create sequence id_gestor_seq;

create table sch_contratos.gestor (
	cpf character varying(11) not null,
	nome character varying(60) not null,
	email character varying(60) not null,
	constraint pk_gestor primary key (cpf)

);


drop table if exists sch_contratos.sindico

create sequence id_sindico_seq;

create table sch_contratos.sindico (
	cpf character varying(11) not null,
	nome character varying(60) not null,
	email character varying(60) not null,
	constraint pk_sindico primary key (cpf)

);


drop table if exists sch_contratos.responsavel

create sequence id_responsavel_seq;

create table sch_contratos.responsavel (
	cpf character varying(11) not null,
	nome character varying(60) not null,
	email character varying(60) not null,
	constraint pk_responsavel primary key (cpf)

);


drop table if exists sch_contratos.rescisao

create sequence id_rescisao_seq;

create table sch_contratos.rescisao (
	id bigint not null default nextval('id_rescisao_seq'),
	causador character varying(60) not null,
	valor numeric(15,2) not null check(valor >=0),
	pct_multa character varying(10) not null,
	dt_pgto date not null,
	dt_rescisao date not null,
	constraint pk_rescisao primary key (id)
);


drop table if exists sch_contratos.fornecedor

create sequence id_fornecedor_seq;

create table sch_contratos.fornecedor (
	id bigint not null default nextval('id_fornecedor_seq'),
	razao_social character varying(80) not null,
	cnpj character varying(14) not null,
	num_contr character varying(10),
	natureza_servico character varying(30) not null,
	status character varying(30) not null,
	endereco character varying(80) not null,
	email_corporativo character varying(60) not null,
	fk_cpf_responsavel character varying(11) not null,
	constraint pk_fornecedor primary key (id),

	constraint fk_id_fornecedor foreign key (fk_cpf_responsavel)
	references sch_contratos.responsavel (cpf) match simple
	on delete cascade
	on update cascade
);


drop table if exists sch_contratos.domicilio_bancario

create sequence id_domicilio_bancario_seq;

create table sch_contratos.domicilio_bancario (
	id bigint not null default nextval('id_domicilio_bancario_seq'),
	agencia character varying(10) not null,
	fk_id_fornecedor integer not null,
	conta character varying(10),
	banco character varying(20),
	chave_pix character varying(50),
	status character varying(30),
	constraint pk_domicilio_bancario primary key (id),

	constraint fk_domicilio_bancario foreign key (fk_id_fornecedor)
	references sch_contratos.fornecedor (id) match simple
	on delete cascade
	on update cascade

);


drop table if exists sch_contratos.contrato

create sequence id_contrato_seq;

create table sch_contratos.contrato (
	id bigint not null default nextval('id_contrato_seq'),
	fk_id_rescisao integer,
	fk_id_fornecedor integer not null,
	vigencia_inicial date not null,
	vigencia_final date not null check (vigencia_final > vigencia_inicial),
	num_contrato character varying(10) not null,
	pct_multa character varying(10),
	objeto character varying(100) not null,
	vlr_mensal_atual numeric(15,2) check(vlr_mensal_atual >=0),
	vlr_mensal_inicial numeric(15,2) check(vlr_mensal_inicial >=0),
	status character varying(30) not null,
	fk_cpf_gestor character varying(11) not null,
	fk_cpf_sindico character varying(11) not null,
	constraint pk_contrato primary key (id),

	constraint fk_id_fornecedor foreign key (fk_id_fornecedor)
	references sch_contratos.fornecedor (id) match simple
	on delete cascade
	on update cascade,

	constraint fk_id_rescisao foreign key (fk_id_rescisao)
	references sch_contratos.rescisao (id) match simple
	on delete cascade
	on update cascade,


	constraint fk_cpf_gestor foreign key (fk_cpf_gestor)
	references sch_contratos.gestor (cpf) match simple
	on delete cascade
	on update cascade,


	constraint fk_cpf_sindico foreign key (fk_cpf_sindico)
	references sch_contratos.sindico (cpf) match simple
	on delete cascade
	on update cascade

);


drop table if exists sch_financeiro.pagamento;

create sequence id_pagamento_seq;

create table sch_financeiro.pagamento (
	id bigint not null default nextval('id_pagamento_seq'),
	fk_usuario integer not null,
	data_hora timestamp not null,
	fk_contrato integer not null,
	aprovacao boolean,
	valor_pagamento numeric(15,2) not null check(valor_pagamento>=0),
	status character varying(30) not null,
	pct_juros numeric(15,2) check(pct_juros>=0),
	valor_juros numeric(15,2) check(valor_juros>=0),
	valor_total numeric(15,2) not null check(valor_total>=0),
	constraint pk_pagamento primary key (id),

	constraint fk_id_usuario foreign key (fk_usuario)
	references sch_pessoas.usuario (id) match simple
	on delete cascade
	on update cascade,

	constraint fk_id_contrato foreign key (fk_contrato)
	references sch_contratos.contrato (id) match simple
	on delete cascade
	on update cascade

);


drop table if exists sch_financeiro.fatura;

create sequence id_fatura_seq;

create table sch_financeiro.fatura (
	id bigint not null default nextval('id_pagamento_seq'),
	fk_pagamento integer not null,
	vencimento date not null check (vencimento >= now()),
	valor numeric(15,2) check(valor>=0),
	numero integer not null,
	status character varying(30) not null,
	constraint pk_fatura primary key (id),

	constraint fk_id_pagamento foreign key (fk_pagamento)
	references sch_financeiro.pagamento (id) match simple
	on delete cascade
	on update cascade
);


-- VIEWS

-- contratos dentro do ano corrente

create or replace view sch_contratos.vw_contratos_ano_corrente as
select c.num_contrato, c.vigencia_inicial, c.vigencia_final, c.objeto, c.vlr_mensal_inicial, c.vlr_mensal_atual, g.nome as nome_gestor, g.email as email_gestor, s.nome as nome_sindico, s.email as email_sindico
from sch_contratos.contrato as c
left join sch_contratos.gestor as g on c.fk_cpf_gestor = g.cpf
left join sch_contratos.sindico as s on c.fk_cpf_sindico = s.cpf
where
(select extract (year from c.vigencia_final) from sch_contratos.contrato) = (select extract (year from now()));

-- contratos ativos

create or replace view sch_contratos.vw_contratos_ativos as
select c.num_contrato, c.vigencia_inicial, c.vigencia_final, c.objeto, c.vlr_mensal_inicial, c.vlr_mensal_atual, g.nome as nome_gestor, g.email as email_gestor, s.nome as nome_sindico, s.email as email_sindico
from sch_contratos.contrato as c
left join sch_contratos.gestor as g on c.fk_cpf_gestor = g.cpf
left join sch_contratos.sindico as s on c.fk_cpf_sindico = s.cpf
where c.status like '%ativo%' or c.status like 'Ativo' or c.status like 'ativo%' or c.status like 'ATIVO' or c.status like '%ATIVO%' or c.status like 'ATIVA' or c.status like '%ativa%' or c.status like 'Ativa' or c.status like 'ativa%';


-- valor total dos contratos ativos

create or replace view sch_contratos.vw_vlr_total_contratos_ativos as
select sum (vlr_mensal_atual) as vlr_total_atual_contratos_ativos
from sch_contratos.contrato
where status like '%ativo%' or status like 'Ativo' or status like 'ativo%' or status like 'ATIVO' or status like '%ATIVO%' or status like 'ATIVA' or status like '%ativa%' or status like 'Ativa' or status like 'ativa%';


-- contratos iniciados no ano corrente

create or replace view sch_contratos.vw_iniciados_ano_corrente as
select c.num_contrato, c.vigencia_inicial, c.vigencia_final, c.objeto, c.vlr_mensal_inicial, c.vlr_mensal_atual, g.nome as nome_gestor, g.email as email_gestor, s.nome as nome_sindico, s.email as email_sindico
from sch_contratos.contrato as c
left join sch_contratos.gestor as g on c.fk_cpf_gestor = g.cpf
left join sch_contratos.sindico as s on c.fk_cpf_sindico = s.cpf
where  (select extract (year from vigencia_inicial) from sch_contratos.contrato) = (select extract (year from now()));


-- Contratos e seus fornecedores

create or replace view sch_contratos.vw_contrato_fornecedores as
select co.num_contrato, co.objeto, co.vigencia_inicial, co.vigencia_final, co.status as status_contrato,
g.nome as nome_gestor, co.vlr_mensal_inicial, co.vlr_mensal_atual, f.razao_social, f.cnpj, f.natureza_servico, f.email_corporativo, f.endereco, f.status as status_fornecedor
from sch_contratos.contrato as co
left join sch_contratos.fornecedor as f on co.fk_id_fornecedor = f.id
left join sch_contratos.gestor as g on co.fk_cpf_gestor = g.cpf


-- Contratos ativos e seus fornecedores

create or replace view sch_contratos.vw_contrato_ativos_fornecedores as
select co.num_contrato, co.objeto, co.vigencia_inicial, co.vigencia_final, co.status as status_contrato,
g.nome as nome_gestor, co.vlr_mensal_inicial, co.vlr_mensal_atual, f.razao_social, f.cnpj, f.natureza_servico, f.email_corporativo, f.endereco, f.status as status_fornecedor
from sch_contratos.contrato as co
left join sch_contratos.fornecedor as f on co.fk_id_fornecedor = f.id
left join sch_contratos.gestor as g on co.fk_cpf_gestor = g.cpf
where co.status like '%ativo%' or co.status like 'Ativo' or co.status like 'ativo%' or co.status like 'ATIVO' or co.status like '%ATIVO%' or co.status like 'ATIVA' or co.status like '%ativa%' or co.status like 'Ativa' or co.status like 'ativa%';



-- Contratos ativos e seus fornecedores/contas bancárias

create or replace view sch_contratos.vw_contrato_ativos_fornecedores_domic_bancario as
select co.num_contrato, co.objeto, co.vigencia_inicial, co.vigencia_final, co.status as status_contrato,
g.nome as nome_gestor, co.vlr_mensal_inicial, co.vlr_mensal_atual, f.razao_social, f.cnpj, f.natureza_servico, f.email_corporativo, f.endereco, f.status as status_fornecedor, d.agencia, d.conta, d.banco, d.chave_pix, d.status as status_conta
from sch_contratos.contrato as co
left join sch_contratos.fornecedor as f on co.fk_id_fornecedor = f.id
left join sch_contratos.domicilio_bancario as d on co.fk_id_fornecedor = d.fk_id_fornecedor
left join sch_contratos.gestor as g on co.fk_cpf_gestor = g.cpf



-- TRIGGER

drop table if exists sch_pessoas.log_pessoa
create sequence id_log_pessoas_seq;
create table sch_pessoas.log_pessoa (
	log_id bigint not null default nextval('id_log_pessoas_seq'),
	log_data_alteracao timestamp,
	log_alteracao varchar(20),

	identificador_old integer,
	cpf_old character varying(11),
	nome_old character varying(60),
	email_old character varying(60),
	status_old character varying(30),

	identificador_new integer,
	cpf_new character varying(11),
	nome_new character varying(60),
	email_new character varying(60),
	status_new character varying(30),

	constraint log_pk_pessoa primary key (log_id)
);


create or replace function gera_log_pessoas()
returns trigger as
$$
begin

	if tg_op = 'INSERT' then
		insert into sch_pessoas.log_pessoa (
					log_data_alteracao,
					log_alteracao,
					identificador_new,
					cpf_new,
					nome_new,
					email_new,
					status_new
		)
	values (
					now(),
					tg_op,
					new.id,
					new.cpf,
					new.nome,
					new.email,
					new.status
	);
		return new;

	elsif tg_op = 'UPDATE' then
		insert into sch_pessoas.log_pessoa (
			log_data_alteracao,
			log_alteracao,
			identificador_old,
			cpf_old ,
			nome_old ,
			email_old,
			status_old,
			identificador_new,
			cpf_new,
			nome_new,
			email_new,
			status_new
		)
	values(
		now(),
		tg_op,
		old.id,
		old.cpf,
		old.nome,
		old.email,
		old.status,
		new.id,
		new.cpf,
		new.nome,
		new.email,
		new.status

	);
		return new;

	elsif tg_op = 'DELETE' then
		insert into sch_pessoas.log_pessoa (
			log_data_alteracao,
			log_alteracao,
			old.id,
			old.cpf,
			old.nome,
			old.email,
			old.status
	)
	values(
			now(),
			tg_op,
			old.id,
			old.cpf,
			old.nome,
			old.email,
			old.status

	);
		return new;
	end if;
end;
$$
language 'plpgsql';



create trigger tri_log_pessoas
after insert or update or delete on sch_pessoas.pessoa
for each row execute
procedure gera_log_pessoas();



-- ==========================================================================================

drop table if exists sch_pessoas.log_apartamento;
create sequence id_log_apartamento_seq;
create table sch_pessoas.log_apartamento (
	log_id bigint not null default nextval('id_log_apartamento_seq'),
	log_data_alteracao timestamp,
	log_alteracao varchar(20),

	identificador_old integer,
	fk_proprietario_old integer,
	fk_morador_old integer,
	metragem_old numeric (10, 2),
	bloco_old character varying(10),
	numero_old integer,
	status_old character varying(30),

	identificador_new integer,
	fk_proprietario_new integer,
	fk_morador_new integer,
	metragem_new numeric (10, 2),
	bloco_new character varying(10),
	numero_new integer,
	status_new character varying(30),

	constraint log_pk_apartamento primary key (log_id)

);


create or replace function gera_log_apartamento()
returns trigger as
$$
begin
	if tg_op = 'INSERT' then
		insert into sch_pessoas.log_apartamento(
			log_data_alteracao,
			log_alteracao,
			identificador_new ,
			fk_proprietario_new,
			fk_morador_new ,
			metragem_new ,
			bloco_new ,
			numero_new ,
			status_new
		)
	values (
			now(),
			tg_op,
			new.id ,
			new.fk_proprietario,
			new.fk_morador ,
			new.metragem ,
			new.bloco ,
			new.numero ,
			new.status
	);
		return new;

	elsif tg_op = 'UPDATE' then
		insert into sch_pessoas.log_apartamento(
			log_data_alteracao,
			log_alteracao,
			identificador_old,
			fk_proprietario_old,
			fk_morador_old ,
			metragem_old ,
			bloco_old ,
			numero_old ,
			status_old,
			identificador_new ,
			fk_proprietario_new,
			fk_morador_new ,
			metragem_new ,
			bloco_new ,
			numero_new ,
			status_new
			)
		values (
			now(),
			tg_op,
			old.id,
			old.fk_proprietario,
			old.fk_morador ,
			old.metragem ,
			old.bloco ,
			old.numero ,
			old.status,
			new.id,
			new.fk_proprietario,
			new.fk_morador ,
			new.metragem ,
			new.bloco ,
			new.numero ,
			new.status
		);
			return new;
	elsif tg_op = 'DELETE' then
		insert into sch_pessoas.log_apartamento(
			log_data_alteracao,
			log_alteracao,
			identificador_old ,
			fk_proprietario_old,
			fk_morador_old ,
			metragem_old ,
			bloco_old ,
			numero_old ,
			status_old
			)
		values(
			now(),
			tg_op,
			old.id ,
			old.fk_proprietario,
			old.fk_morador ,
			old.metragem ,
			old.bloco ,
			old.numero ,
			old.status
		);
			return new;
	end if;
end;
$$
language 'plpgsql';

create trigger tri_log_apartamento
after insert or update or delete on sch_pessoas.apartamento
for each row execute
procedure gera_log_apartamento();



-- =============================================================================================

drop table if exists sch_financeiro.log_pagamento;
create sequence id_log_pagamento_seq;
create table sch_financeiro.log_pagamento (
	log_id bigint not null default nextval('id_log_pagamento_seq'),
	log_data_alteracao timestamp,
	log_alteracao varchar(20),

	identificador_old integer,
	fk_usuario_old integer,
	data_hora_old timestamp,
	fk_contrato_old integer ,
	aprovacao_old boolean,
	valor_pagamento_old numeric(15,2),
	status_old character varying(30),
	pct_juros_old numeric(15,2) ,
	valor_juros_old numeric(15,2) ,
	valor_total_old numeric(15,2),

	identificador_new integer,
	fk_usuario_new integer,
	data_hora_new timestamp,
	fk_contrato_new integer ,
	aprovacao_new boolean,
	valor_pagamento_new numeric(15,2),
	status_new character varying(30),
	pct_juros_new numeric(15,2) ,
	valor_juros_new numeric(15,2) ,
	valor_total_new numeric(15,2),

	constraint log_pk_pagamento primary key (log_id)
);


create or replace function gera_log_pagamento()
returns trigger as
$$
begin
	if tg_op = 'INSERT' then
		insert into sch_financeiro.log_pagamento(
			log_data_alteracao,
			log_alteracao,
			identificador_new,
			fk_usuario_new,
			data_hora_new ,
			fk_contrato_new ,
			aprovacao_new ,
			valor_pagamento_new,
			status_new,
			pct_juros_new,
			valor_juros_new,
			valor_total_new
)
		values (
			now(),
			tg_op,
			new.id,
			new.fk_usuario,
			new.data_hora ,
			new.fk_contrato ,
			new.aprovacao,
			new.valor_pagamento,
			new.status,
			new.pct_juros,
			new.valor_juros,
			new.valor_total
);
	return new;

	elsif tg_op = 'UPDATE' then
		insert into sch_financeiro.log_pagamento(
			log_data_alteracao,
			log_alteracao,
			identificador_old,
			fk_usuario_old,
			data_hora_old,
			fk_contrato_old,
			aprovacao_old,
			valor_pagamento_old,
			status_old,
			pct_juros_old,
			valor_juros_old,
			valor_total_old,
			identificador_new,
			fk_usuario_new,
			data_hora_new ,
			fk_contrato_new ,
			aprovacao_new ,
			valor_pagamento_new,
			status_new,
			pct_juros_new,
			valor_juros_new,
			valor_total_new
)
	values (
			now(),
			tg_op,
			old.id,
			old.fk_usuario,
			old.data_hora ,
			old.fk_contrato ,
			old.aprovacao,
			old.valor_pagamento,
			old.status,
			old.pct_juros,
			old.valor_juros,
			old.valor_total,
			new.pk_id,
			new.fk_usuario,
			new.data_hora ,
			new.fk_contrato ,
			new.aprovacao,
			new.valor_pagamento,
			new.status,
			new.pct_juros,
			new.valor_juros,
			new.valor_total

	);
	 	return new;

	elsif tg_op = 'DELETE' then
		insert into sch_financeiro.log_pagamento(
			log_data_alteracao,
			log_alteracao,
			identificador_old,
			fk_usuario_old,
			data_hora_old,
			fk_contrato_old,
			aprovacao_old,
			valor_pagamento_old,
			status_old,
			pct_juros_old,
			valor_juros_old,
			valor_total_old

)
	values (
			now(),
			tg_op,
			old.id,
			old.fk_usuario,
			old.data_hora ,
			old.fk_contrato ,
			old.aprovacao,
			old.valor_pagamento,
			old.status,
			old.pct_juros,
			old.valor_juros,
			old.valor_total

	);
		return new;
	end if;
end;
$$
language 'plpgsql';


create trigger tri_log_pagamento
after insert or update or delete on sch_financeiro.pagamento
for each row execute
procedure gera_log_pagamento();



-- =============================================================================================================


drop table if exists sch_contratos.log_fornecedor;
create sequence id_log_fornecedor_seq;
create table sch_contratos.log_fornecedor (
	log_id bigint not null default nextval('id_log_fornecedor_seq'),
	log_data_alteracao timestamp,
	log_alteracao varchar(20),

	identificador_old integer,
	razao_social_old character varying(80),
	cnpj_old character varying(16),
	num_contr_old character varying(10),
	natureza_servico_old character varying(30),
	status_old character varying(30),
	endereco_old character varying(80),
	email_corporativo_old character varying(60),
	fk_cpf_responsavel_old character varying(11),

	identificador_new integer,
	razao_social_new character varying(80),
	cnpj_new character varying(16),
	num_contr_new character varying(10),
	natureza_servico_new character varying(30),
	status_new character varying(30),
	endereco_new character varying(80),
	email_corporativo_new character varying(60),
	fk_cpf_responsavel_new character varying(11),

	constraint log_pk_fornecedor primary key (log_id)
);


create or replace function gera_log_fornecedor()
returns trigger as
$$
begin
	if tg_op = 'INSERT' then
		insert into sch_contratos.log_fornecedor(
			log_data_alteracao,
			log_alteracao,
			identificador_new,
			razao_social_new,
			cnpj_new,
			num_contr_new,
			natureza_servico_new,
			status_new,
			endereco_new,
			email_corporativo_new,
			fk_cpf_responsavel_new
)
	values(
			now(),
			tg_op,
			new.id,
			new.razao_social,
			new.cnpj,
			new.num_contr,
			new.natureza_servico,
			new.status,
			new.endereco,
			new.email_corporativo,
			new.fk_cpf_responsavel
);
	return new;

	elsif tg_op = 'UPDATE' then
		insert into sch_contratos.log_fornecedor(
			log_data_alteracao,
			log_alteracao,
			identificador_old,
			razao_social_old,
			cnpj_old,
			num_contr_old,
			natureza_servico_old,
			status_old,
			endereco_old,
			email_corporativo_old,
			fk_cpf_responsavel_old,
			identificador_new,
			razao_social_new,
			cnpj_new,
			num_contr_new,
			natureza_servico_new,
			status_new,
			endereco_new,
			email_corporativo_new,
			fk_cpf_responsavel_new

)
	values (
			now(),
			tg_op,
			old.id,
			old.razao_social,
			old.cnpj,
			old.num_contr,
			old.natureza_servico,
			old.status,
			old.endereco,
			old.email_corporativo,
			old.fk_cpf_responsavel,
			new.id,
			new.razao_social,
			new.cnpj,
			new.num_contr,
			new.natureza_servico,
			new.status,
			new.endereco,
			new.email_corporativo,
			new.fk_cpf_responsavel
);

	return new;
	elsif tg_op = 'DELETE' then
		insert into sch_contratos.log_fornecedor(
			log_data_alteracao,
			log_alteracao,
			identificador_old,
			razao_social_old,
			cnpj_old,
			num_contr_old,
			natureza_servico_old,
			status_old,
			endereco_old,
			email_corporativo_old,
			fk_cpf_responsavel_old

)
	values (
			now(),
			tg_op,
			old.id,
			old.razao_social,
			old.cnpj,
			old.num_contr,
			old.natureza_servico,
			old.status,
			old.endereco,
			old.email_corporativo,
			old.fk_cpf_responsavel
);
	return new;
	end if;
end;
$$
language 'plpgsql';

create trigger tri_log_fornecedor
after insert or update or delete on sch_contratos.fornecedor
for each row execute
procedure gera_log_fornecedor();


-- =======================================================================

drop table if exists sch_contratos.log_contrato;
create sequence id_log_contrato_seq;
create table sch_contratos.log_contrato(
	log_id bigint not null default nextval('id_log_contrato_seq'),
	log_data_alteracao timestamp,
	log_alteracao varchar(20),

	identificador_old integer,
	fk_id_rescisao_old integer ,
	fk_id_fornecedor_old integer ,
	vigencia_inicial_old date ,
	vigencia_final_old date ,
	num_contrato_old character varying(10),
	pct_multa_old character varying(10),
	objeto_old character varying(100),
	vlr_mensal_atual_old numeric(15,2) ,
	vlr_mensal_inicial_old numeric(15,2),
	status_old character varying(30) ,
	fk_cpf_gestor_old character varying(15) ,
	fk_cpf_sindico_old character varying(15) ,

	identificador_new integer,
	fk_id_rescisao_new integer ,
	fk_id_fornecedor_new integer ,
	vigencia_inicial_new date ,
	vigencia_final_new date ,
	num_contrato_new character varying(10),
	pct_multa_new character varying(10),
	objeto_new character varying(100),
	vlr_mensal_atual_new numeric(15,2) ,
	vlr_mensal_inicial_new numeric(15,2),
	status_new character varying(30),
	fk_cpf_gestor_new character varying(15),
	fk_cpf_sindico_new character varying(15),

	constraint log_pk_contrato primary key (log_id)
);


create or replace function gera_log_contrato()
returns trigger as
$$
begin
	if tg_op = 'INSERT' then
		insert into sch_contratos.log_contrato(
			log_data_alteracao,
			log_alteracao,
			identificador_new,
			fk_id_rescisao_new ,
			fk_id_fornecedor_new ,
			vigencia_inicial_new ,
			vigencia_final_new ,
			num_contrato_new ,
			pct_multa_new ,
			objeto_new ,
			vlr_mensal_atual_new ,
			vlr_mensal_inicial_new ,
			status_new  ,
			fk_cpf_gestor_new  ,
			fk_cpf_sindico_new
)
	values (
			now(),
			tg_op,
			new.id,
			new.fk_id_rescisao,
			new.fk_id_fornecedor,
			new.vigencia_inicial,
			new.vigencia_final,
			new.num_contrato,
			new.pct_multa,
			new.objeto,
			new.vlr_mensal_atual ,
			new.vlr_mensal_inicial ,
			new.status ,
			new.fk_cpf_gestor ,
			new.fk_cpf_sindico
);
	return new;

	elsif tg_op = 'UPDATE' then
		insert into sch_contratos.log_contrato(
			log_data_alteracao,
			log_alteracao,
			identificador_old ,
			fk_id_rescisao_old ,
			fk_id_fornecedor_old  ,
			vigencia_inicial_old ,
			vigencia_final_old  ,
			num_contrato_old ,
			pct_multa_old ,
			objeto_old ,
			vlr_mensal_atual_old  ,
			vlr_mensal_inicial_old,
			status_old  ,
			fk_cpf_gestor_old  ,
			fk_cpf_sindico_old  ,
			identificador_new,
			fk_id_rescisao_new ,
			fk_id_fornecedor_new ,
			vigencia_inicial_new ,
			vigencia_final_new ,
			num_contrato_new ,
			pct_multa_new ,
			objeto_new ,
			vlr_mensal_atual_new ,
			vlr_mensal_inicial_new ,
			status_new  ,
			fk_cpf_gestor_new  ,
			fk_cpf_sindico_new
)
	values (
			now(),
			tg_op,
			old.id,
			old.fk_id_rescisao,
			old.fk_id_fornecedor,
			old.vigencia_inicial,
			old.vigencia_final,
			old.num_contrato,
			old.pct_multa,
			old.objeto,
			old.vlr_mensal_atual ,
			old.vlr_mensal_inicial ,
			old.status ,
			old.fk_cpf_gestor ,
			old.fk_cpf_sindico ,
			new.id,
			new.fk_id_rescisao,
			new.fk_id_fornecedor,
			new.vigencia_inicial,
			new.vigencia_final,
			new.num_contrato,
			new.pct_multa,
			new.objeto,
			new.vlr_mensal_atual ,
			new.vlr_mensal_inicial ,
			new.status ,
			new.fk_cpf_gestor ,
			new.fk_cpf_sindico
);
	return new;

	elsif tg_op = 'DELETE' then
		insert into sch_contratos.log_contrato(
			log_data_alteracao,
			log_alteracao,
			identificador_old ,
			fk_id_rescisao_old ,
			fk_id_fornecedor_old  ,
			vigencia_inicial_old ,
			vigencia_final_old  ,
			num_contrato_old ,
			pct_multa_old ,
			objeto_old ,
			vlr_mensal_atual_old  ,
			vlr_mensal_inicial_old,
			status_old  ,
			fk_cpf_gestor_old  ,
			fk_cpf_sindico_old

)
	values (
			now(),
			tg_op,
			old.id,
			old.fk_id_rescisao,
			old.fk_id_fornecedor,
			old.vigencia_inicial,
			old.vigencia_final,
			old.num_contrato,
			old.pct_multa,
			old.objeto,
			old.vlr_mensal_atual ,
			old.vlr_mensal_inicial ,
			old.status ,
			old.fk_cpf_gestor ,
			old.fk_cpf_sindico

);
	return new;
	end if;
end;
$$
language 'plpgsql';


create trigger tri_log_contrato
after insert or update or delete on sch_contratos.contrato
for each row execute
procedure gera_log_contrato();
