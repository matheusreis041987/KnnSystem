package com.knnsystem.api.service;

import com.knnsystem.api.dto.ApartamentoFormularioDTO;

import java.util.List;

public interface ApartamentoService {

	List<ApartamentoFormularioDTO> listar();

	ApartamentoFormularioDTO salvar(ApartamentoFormularioDTO apartamentoFormularioDTO);

}
