alter table sch_contratos.contrato
alter column pct_multa TYPE numeric(5, 2)
USING  pct_multa::numeric(5,2);