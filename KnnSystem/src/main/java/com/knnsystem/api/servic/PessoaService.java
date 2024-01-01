package com.knnsystem.api.servic;

import java.util.List;
import java.util.Optional;

import com.knnsystem.api.model.entity.Pessoa;


public interface PessoaService {
		
	Pessoa salvar (Pessoa PessoaParm);
	
	Pessoa atualizar (Pessoa PessoaParm);
	
	void deletar (Pessoa PessoaParm);
	
	List<Pessoa> buscar(Pessoa PessoaParm);

	Optional<Pessoa> consultarPorId (Integer idPessoa);
	

	
	
}
