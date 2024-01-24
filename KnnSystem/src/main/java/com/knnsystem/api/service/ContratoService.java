package com.knnsystem.api.service;

import java.util.List;
import java.util.Optional;

import com.knnsystem.api.dto.ContratoCadastroDTO;
import com.knnsystem.api.model.entity.Contrato;



public interface ContratoService {

	ContratoCadastroDTO salvar (ContratoCadastroDTO dto);
	

}
