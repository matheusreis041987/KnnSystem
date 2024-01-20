package com.knnsystem.api.infrastructure.api.financeiro.impl;

import com.knnsystem.api.dto.ExtratoFinanceiroDTO;
import com.knnsystem.api.infrastructure.api.financeiro.ApiExtratoFinanceiroService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ApiExtratoFinanceiroServiceCsvImpl implements ApiExtratoFinanceiroService {
    @Override
    public ExtratoFinanceiroDTO gerar(LocalDate dataInicio, LocalDate dataFim) {
        return null;
    }
}
