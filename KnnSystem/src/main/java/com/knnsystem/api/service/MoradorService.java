package com.knnsystem.api.service;

import com.knnsystem.api.dto.MoradorDTO;

import java.util.List;

public interface MoradorService {

	MoradorDTO salvar (MoradorDTO moradorDTO);

	List<MoradorDTO> listar(String cpf, String nome);

	MoradorDTO atualizar(Long id, MoradorDTO dto);
}
