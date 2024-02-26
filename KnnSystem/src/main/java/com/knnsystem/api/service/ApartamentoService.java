package com.knnsystem.api.service;

import com.knnsystem.api.dto.ApartamentoFormularioDTO;

import java.util.List;

public interface ApartamentoService {

	List<ApartamentoFormularioDTO> listar();

	List<ApartamentoFormularioDTO> listar(Integer numero, String bloco);

	ApartamentoFormularioDTO salvar(ApartamentoFormularioDTO apartamentoFormularioDTO);

	void inativar(Long id);

    void excluir(Long id);

	ApartamentoFormularioDTO atualizar(Long id, ApartamentoFormularioDTO dto);
}
