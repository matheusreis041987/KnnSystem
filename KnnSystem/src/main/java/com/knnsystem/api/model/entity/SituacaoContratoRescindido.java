package com.knnsystem.api.model.entity;

import com.knnsystem.api.exceptions.RegraNegocioException;

public class SituacaoContratoRescindido extends  SituacaoContrato{
    @Override
    void ativar(Contrato contrato) {
        throw new RegraNegocioException("Não é possível ativar contrato rescindido");
    }

    @Override
    void inativar(Contrato contrato) {
        throw new RegraNegocioException("Não é possível inativar contrato rescindido");
    }

    @Override
    void rescindir(Contrato contrato) {
        throw new RegraNegocioException("Não é possível rescindir contrato rescindido");
    }

    @Override
    void renovar(Contrato contrato) {
        throw new RegraNegocioException("Não é possível renovar contrato rescindido");
    }
}
