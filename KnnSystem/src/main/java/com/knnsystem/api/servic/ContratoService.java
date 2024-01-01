package com.knnsystem.api.servic;

import java.util.List;
import java.util.Optional;

import com.knnsystem.api.model.entity.Contrato;



public interface ContratoService {

	Contrato salvar (Contrato contratoParam);
	
	Contrato atualizar (Contrato contratoParam);
	
	void deletar (Contrato contratoParam);
	
	List<Contrato> buscar(Contrato contratoParam);

	Optional<Contrato> consultarPorId (Integer idContrato);
	
	
}
