package com.knnsystem.api.service;

import java.util.List;
import java.util.Optional;

import com.knnsystem.api.model.entity.PagamentoPix;


public interface PagamentoPixService {

	PagamentoPix salvar (PagamentoPix PagamentoPixParm);
	
	PagamentoPix atualizar (PagamentoPix PagamentoPixParm);
	
	void deletar (PagamentoPix PagamentoPixParm);
	
	List<PagamentoPix> buscar(PagamentoPix PagamentoPixParm);

	Optional<PagamentoPix> consultarPorId (Integer idPagamentoPix);
	
}
