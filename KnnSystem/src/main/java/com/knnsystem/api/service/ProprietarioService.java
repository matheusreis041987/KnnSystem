package com.knnsystem.api.service;

import java.util.List;
import java.util.Optional;

import com.knnsystem.api.model.entity.Proprietario;


public interface ProprietarioService {

	Proprietario salvar (Proprietario ProprietarioParm);
	
	Proprietario atualizar (Proprietario ProprietarioParm);
	
	void deletar (Proprietario ProprietarioParm);
	
	List<Proprietario> buscar(Proprietario ProprietarioParm);

	Optional<Proprietario> consultarPorId (Integer idProprietario);
	
}
