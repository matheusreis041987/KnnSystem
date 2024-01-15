package com.knnsystem.api.service;

import com.knnsystem.api.model.entity.Apartamento;

import java.util.List;
import java.util.Optional;

public interface ApartamentoService {
	
	Apartamento salvar (Apartamento apartamentoParam);
	
	Apartamento atualizar (Apartamento apartamentoParm);
	
	void deletar (Apartamento apartamentoParm);
	
	List<Apartamento> buscar(Apartamento apartamentoParm);

	Optional<Apartamento> consultarPorId (Integer idApartamento);
	
}
