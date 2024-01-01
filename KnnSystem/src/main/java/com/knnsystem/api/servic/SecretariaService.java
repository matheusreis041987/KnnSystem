package com.knnsystem.api.servic;

import java.util.List;
import java.util.Optional;

import com.knnsystem.api.model.entity.Secretaria;

public interface SecretariaService {

	Secretaria salvar (Secretaria SecretariaParm);
	
	Secretaria atualizar (Secretaria SecretariaParm);
	
	void deletar (Secretaria SecretariaParm);
	
	List<Secretaria> buscar(Secretaria SecretariaParm);

	Optional<Secretaria> consultarPorId (Integer idSecretaria);
	
}
