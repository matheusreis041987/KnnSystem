package com.knnsystem.api.servic;

import java.util.List;
import java.util.Optional;

import com.knnsystem.api.model.entity.VwContratosAtivos;


public interface VwContratosAtivosService {

	List<VwContratosAtivos> buscar(VwContratosAtivos VwContratosAtivosParm);

	
	
}
