package com.knnsystem.api.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.br.CNPJ;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FaturaCadastroDTO(
        @NotBlank(message = "Número do contrato deve ser informado")
        String numeroContrato,

        @NotNull(message = "Número da fatura deve ser informada")
        @Positive
        Long numeroFatura,

        @NotBlank(message = "CNPJ do fornecedor deve ser informado")
        @CNPJ
        String cnpjFornecedor,

        @NotBlank(message = "Razão social do fornecedor deve ser informada")
        String razaoSocial,

        @NotNull(message = "Valor da fatura deve ser informado")
        @Positive
        BigDecimal valor,

        @NotNull(message = "Data de pagamento deve ser informada")
        LocalDate dataPagamento,

        @NotNull(message = "Domícilio bancário deve ser informado")
        DomicilioBancarioDTO domicilioBancario

) {
        public DadosPagamentoDTO getDadosPagamentos() {
                return new DadosPagamentoDTO(
                        dataPagamento(),
                        valor(),
                        domicilioBancario());
        }
}
