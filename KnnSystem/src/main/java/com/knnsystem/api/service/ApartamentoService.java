package com.knnsystem.api.service;

import com.knnsystem.api.dto.ApartamentoFormularioDTO;

import java.util.List;

public interface ApartamentoService {

	List<ApartamentoFormularioDTO> listar();

	List<ApartamentoFormularioDTO> listar(Integer numero, String bloco);

	ApartamentoFormularioDTO salvar(ApartamentoFormularioDTO apartamentoFormularioDTO);

	ApartamentoFormularioDTO inativar(Integer numero, String bloco);
}
