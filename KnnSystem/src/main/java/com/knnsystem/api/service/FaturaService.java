package com.knnsystem.api.service;

import java.util.List;
import java.util.Optional;

import com.knnsystem.api.model.entity.Fatura;



public interface FaturaService {

	Fatura salvar (Fatura FaturaParm);
	
	Fatura atualizar (Fatura FaturaParm);
	
	void deletar (Fatura FaturaParm);
	
	List<Fatura> buscar(Fatura FaturaParm);

	Optional<Fatura> consultarPorId (Integer idFatura);
	
}
