package com.knnsystem.api.service;

import com.knnsystem.api.dto.FaturaCadastroDTO;
import com.knnsystem.api.dto.FaturaResultadoDTO;
import com.knnsystem.api.dto.ResultadoPagamentoDTO;

import java.util.List;


public interface FaturaService {

	ResultadoPagamentoDTO salvar (FaturaCadastroDTO dto);

	List<FaturaResultadoDTO> listar(String cnpjFornecedor, String razaoSocial, String numeroContrato, Long numeroFatura);

	void inativar(Long id);
}
