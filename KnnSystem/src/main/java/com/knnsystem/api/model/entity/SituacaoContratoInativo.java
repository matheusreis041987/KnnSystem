package com.knnsystem.api.model.entity;

import com.knnsystem.api.exceptions.RegraNegocioException;

public class SituacaoContratoInativo extends SituacaoContrato{
    @Override
    void ativar(Contrato contrato) {
        contrato.setStatusContrato(StatusContrato.ATIVO);
    }

    @Override
    void inativar(Contrato contrato) {
        throw new RegraNegocioException("Erro - contrato não pode ser inativado");

    }

    @Override
    void rescindir(Contrato contrato) {
        throw new RegraNegocioException("Erro - contrato não pode ser rescindido");
    }

    @Override
    void renovar(Contrato contrato) {
        throw new RegraNegocioException("Erro - contrato não pode ser renovado");
    }
}
