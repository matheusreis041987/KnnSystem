package com.knnsystem.api.service;

import java.util.List;
import java.util.Optional;

import com.knnsystem.api.model.entity.Pagamento;


public interface PagamentoService {

	Pagamento salvar (Pagamento PagamentoParm);
	
	Pagamento atualizar (Pagamento PagamentoParm);
	
	void deletar (Pagamento PagamentoParm);
	
	List<Pagamento> buscar(Pagamento PagamentoParm);

	Optional<Pagamento> consultarPorId (Integer idPagamento);
	
}
