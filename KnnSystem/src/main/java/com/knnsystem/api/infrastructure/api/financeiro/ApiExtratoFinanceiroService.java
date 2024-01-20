package com.knnsystem.api.infrastructure.api.financeiro;

import com.knnsystem.api.dto.ExtratoFinanceiroDTO;

import java.time.LocalDate;

public interface ApiExtratoFinanceiroService {
    ExtratoFinanceiroDTO gerar(LocalDate dataInicio, LocalDate dataFim);
}
