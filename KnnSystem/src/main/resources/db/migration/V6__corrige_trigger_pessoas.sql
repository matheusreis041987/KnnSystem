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
			identificador_old,
			cpf_old,
			nome_old,
			email_old,
			status_old
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