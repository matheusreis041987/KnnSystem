package com.knnsystem.api.infrastructure.api.documental.impl;

import com.knnsystem.api.infrastructure.api.documental.ApiDocumentoFacade;
import com.knnsystem.api.model.entity.Fornecedor;
import com.knnsystem.api.model.entity.Morador;
import org.springframework.stereotype.Service;

@Service
public class ApiDocumentoFacadeSempreValidoImpl implements ApiDocumentoFacade {
    @Override
    public boolean validarDocumento(String documento) {
        return true;
    }

    @Override
    public String obterDadosDocumento(Fornecedor fornecedor) {
        return null;
    }

    @Override
    public String obterDadosDocumento(Morador morador) {
        return null;
    }
}
