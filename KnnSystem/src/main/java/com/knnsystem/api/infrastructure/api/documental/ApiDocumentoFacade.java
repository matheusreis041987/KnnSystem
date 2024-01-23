package com.knnsystem.api.infrastructure.api.documental;

import com.knnsystem.api.model.entity.Fornecedor;
import com.knnsystem.api.model.entity.Morador;

public interface ApiDocumentoFacade {
    boolean validarDocumento(String documento);

    String obterDadosDocumento(Fornecedor fornecedor);

    String obterDadosDocumento(Morador morador);
}
