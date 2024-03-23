package com.knnsystem.api.service;


import com.knnsystem.api.dto.UsuarioCadastroDTO;
import com.knnsystem.api.dto.UsuarioConsultaDTO;
import com.knnsystem.api.dto.UsuarioResumoDTO;
import com.knnsystem.api.model.entity.Usuario;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

public interface UsuarioService {

	UsuarioResumoDTO salvar (UsuarioCadastroDTO dto);

	UsuarioResumoDTO editar(String cpf, UsuarioCadastroDTO dto);

	UsuarioConsultaDTO consultarPorCPF(@CPF String cpf);

	UsuarioConsultaDTO ativar(@CPF String cpf);

	UsuarioConsultaDTO inativar(@CPF String cpf);

	Usuario getUsuarioLogado();

    void excluir(Long id);

    List<UsuarioConsultaDTO> listar();
	List<UsuarioConsultaDTO> listar(String cpf);
}
