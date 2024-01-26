package com.knnsystem.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosPagamentoDTO(
        @NotNull(message = "data de pagamento deve ser informada")
        LocalDate dataPagamento,

        @NotNull(message = "valor de pagamento deve ser informado")
        @Positive
        BigDecimal valor,

        @NotNull(message = "dados bancários devem ser informados")
        DomicilioBancarioDTO domicilioBancarioDTO
) {
}
