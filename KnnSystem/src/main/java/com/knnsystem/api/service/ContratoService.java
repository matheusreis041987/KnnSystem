package com.knnsystem.api.service;

import com.knnsystem.api.dto.ContratoDTO;

import java.util.List;


public interface ContratoService {

	ContratoDTO salvar (ContratoDTO dto);


	List<ContratoDTO> listar(String cnpjFornecedor, String razaoSocial, String numeroControle);

	void inativar(Long id);

	void reajustar(Long id);
}
