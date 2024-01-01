package com.knnsystem.api.servic;

import java.util.List;
import java.util.Optional;

import com.knnsystem.api.model.entity.PagamentoDeposito;


public interface PagamentoDepositoService {

	
	PagamentoDeposito salvar (PagamentoDeposito PagamentoDepositoParm);
	
	PagamentoDeposito atualizar (PagamentoDeposito PagamentoDepositoParm);
	
	void deletar (PagamentoDeposito PagamentoDepositoParm);
	
	List<PagamentoDeposito> buscar(PagamentoDeposito PagamentoDepositoParm);

	Optional<PagamentoDeposito> consultarPorId (Integer idPagamentoDeposito);
	
	
}
