package com.knnsystem.api.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReajusteParametrosDTO(
        @NotNull(message = "IPCA acumulado é obrigatório")
        BigDecimal ipcaAcumulado,

        @NotNull(message = "Data de reajuste é obrigatória")
        LocalDate data
) {
}
