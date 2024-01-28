package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.DomicilioBancario;
import com.knnsystem.api.model.entity.StatusGeral;
import jakarta.validation.constraints.NotBlank;

public record DomicilioBancarioDTO (
		String agencia,

		String contaCorrente,

		String banco,

		String pix
) {
    public DomicilioBancarioDTO(DomicilioBancario domicilioBancario) {
		this(
				domicilioBancario.getAgencia(),
				domicilioBancario.getContaCorrente(),
				domicilioBancario.getBanco(),
				domicilioBancario.getPix()
		);
    }

	public DomicilioBancario toModel(boolean isInclusao) {
		DomicilioBancario domicilioBancario = new DomicilioBancario();
		domicilioBancario.setPix(pix());
		domicilioBancario.setAgencia(agencia());
		domicilioBancario.setContaCorrente(contaCorrente());
		domicilioBancario.setBanco(banco());

		if (isInclusao){
			domicilioBancario.setStatusDomicilio(StatusGeral.ATIVO);
		}

		return domicilioBancario;
	}
}
