package com.knnsystem.api.model.entity;

import com.knnsystem.api.dto.DomicilioBancarioDTO;

public class PagamentoFactory {
    /*
    * Se consumidor informar pix, o pagamento será por ele
    * */
    public static Pagamento createPagamento(DomicilioBancarioDTO dto) {
        return dto.pix() == null ? new PagamentoPix() : new PagamentoDeposito();
    }
}
