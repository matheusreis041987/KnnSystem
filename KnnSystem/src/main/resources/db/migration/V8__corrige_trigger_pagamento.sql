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
