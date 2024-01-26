package com.knnsystem.api.service;

import com.knnsystem.api.dto.FaturaCadastroDTO;
import com.knnsystem.api.dto.ResultadoPagamentoDTO;



public interface FaturaService {

	ResultadoPagamentoDTO salvar (FaturaCadastroDTO dto);

}
