package com.knnsystem.api.dto;

import jakarta.validation.constraints.NotBlank;

public record DomicilioBancarioDTO (
		@NotBlank
		String agencia,
		@NotBlank
		String contaCorrente,
		@NotBlank
		String banco,
		@NotBlank
		String pix
) {
}
