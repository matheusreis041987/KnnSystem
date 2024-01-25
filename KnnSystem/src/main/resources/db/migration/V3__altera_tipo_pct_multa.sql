alter table sch_contratos.contrato
alter column pct_multa TYPE numeric(3, 2)
USING  pct_multa::numeric(3,2);