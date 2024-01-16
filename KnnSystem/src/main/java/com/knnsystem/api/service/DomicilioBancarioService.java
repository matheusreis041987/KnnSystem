package com.knnsystem.api.service;

import java.util.List;
import java.util.Optional;


import com.knnsystem.api.model.entity.DomicilioBancario;

public interface DomicilioBancarioService {

	DomicilioBancario salvar (DomicilioBancario domicilioParm);
	
	DomicilioBancario atualizar (DomicilioBancario domicilioParm);
	
	void deletar (DomicilioBancario domicilioParm);
	
	List<DomicilioBancario> buscar(DomicilioBancario domicilioParm);

	Optional<DomicilioBancario> consultarPorId (Integer idDomicilio);
	
	
	
}
