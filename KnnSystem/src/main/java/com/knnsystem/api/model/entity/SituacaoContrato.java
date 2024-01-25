package com.knnsystem.api.model.entity;

public abstract class SituacaoContrato {
    abstract void ativar(Contrato contrato);

    abstract void inativar(Contrato contrato);

    abstract void rescindir(Contrato contrato);

    abstract void  renovar(Contrato contrato);

}
