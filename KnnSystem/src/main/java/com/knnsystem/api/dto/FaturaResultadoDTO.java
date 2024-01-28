package com.knnsystem.api.dto;

import org.hibernate.validator.constraints.br.CNPJ;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FaturaResultadoDTO(
        String numeroDoContrato,

        Long numeroDaFatura,

        @CNPJ
        String cnpjDoFornecedor,

        String razaoSocial,

        BigDecimal valor,

        LocalDate competencia
) {
}
