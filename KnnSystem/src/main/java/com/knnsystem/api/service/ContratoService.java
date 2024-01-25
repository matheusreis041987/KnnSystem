package com.knnsystem.api.service;

import com.knnsystem.api.dto.ContratoDTO;
import com.knnsystem.api.dto.ReajusteParametrosDTO;
import com.knnsystem.api.dto.RescisaoCadastroDTO;
import jakarta.validation.Valid;

import java.util.List;


public interface ContratoService {

	ContratoDTO salvar (ContratoDTO dto);


	List<ContratoDTO> listar(String cnpjFornecedor, String razaoSocial, String numeroControle);

	void inativar(Long id);

	void reajustar(Long id, @Valid ReajusteParametrosDTO dto);

    void rescindir(Long id, @Valid RescisaoCadastroDTO dto);
}
