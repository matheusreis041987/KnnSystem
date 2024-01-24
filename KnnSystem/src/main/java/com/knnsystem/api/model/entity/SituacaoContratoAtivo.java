package com.knnsystem.api.model.entity;

import com.knnsystem.api.exceptions.RegraNegocioException;
import lombok.Setter;

public class SituacaoContratoAtivo extends SituacaoContrato{
    @Override
    void ativar(Contrato contrato) {
        throw new RegraNegocioException("Erro - contrato não pode ser ativado");
    }

    @Override
    void inativar(Contrato contrato) {
        contrato.setStatusContrato(StatusContrato.INATIVO);
    }

    @Override
    void rescindir(Contrato contrato) {
        contrato.setStatusContrato(StatusContrato.RESCINDIDO);
    }

    @Override
    void renovar(Contrato contrato) {
        contrato.setStatusContrato(StatusContrato.EM_RENOVACAO);
    }
}
