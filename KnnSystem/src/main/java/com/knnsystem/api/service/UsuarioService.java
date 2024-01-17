package com.knnsystem.api.service;

import java.util.List;
import java.util.Optional;

import com.knnsystem.api.dto.UsuarioCadastroDTO;
import com.knnsystem.api.dto.UsuarioConsultaDTO;
import com.knnsystem.api.dto.UsuarioResumoDTO;
import com.knnsystem.api.model.entity.Usuario;
import org.hibernate.validator.constraints.br.CPF;

public interface UsuarioService {

	UsuarioResumoDTO salvar (UsuarioCadastroDTO dto);

	UsuarioResumoDTO editar(String cpf, UsuarioCadastroDTO dto);

	Optional<UsuarioConsultaDTO> consultarPorCPF(@CPF String cpf);

}
