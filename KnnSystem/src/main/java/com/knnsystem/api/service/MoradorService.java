package com.knnsystem.api.service;

import java.util.List;
import java.util.Optional;

import com.knnsystem.api.model.entity.Morador;

public interface MoradorService {

	
	Morador salvar (Morador MoradorParm);
	
	Morador atualizar (Morador MoradorParm);
	
	void deletar (Morador MoradorParm);
	
	List<Morador> buscar(Morador MoradorParm);

	Optional<Morador> consultarPorId (Integer idMorador);
	
	
	
}
