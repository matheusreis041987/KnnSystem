package com.knnsystem.api.servic;

import java.util.List;
import java.util.Optional;

import com.knnsystem.api.model.entity.Sindico;


public interface SindicoService {

	
	Sindico salvar (Sindico SindicoParm);
	
	Sindico atualizar (Sindico SindicoParm);
	
	void deletar (Sindico SindicoParm);
	
	List<Sindico> buscar(Sindico SindicoParm);

	Optional<Sindico> consultarPorId (Integer idSindico);
	
}
