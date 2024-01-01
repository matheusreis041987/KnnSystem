package com.knnsystem.api.servic;

import java.util.List;
import java.util.Optional;

import com.knnsystem.api.model.entity.Rescisao;


public interface RescisaoService {

	Rescisao salvar (Rescisao RescisaoParm);
	
	Rescisao atualizar (Rescisao RescisaoParm);
	
	void deletar (Rescisao RescisaoParm);
	
	List<Rescisao> buscar(Rescisao RescisaoParm);

	Optional<Rescisao> consultarPorId (Integer idRescisao);
	
	
	
}
