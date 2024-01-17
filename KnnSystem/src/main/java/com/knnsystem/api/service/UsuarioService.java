package com.knnsystem.api.service;

import java.util.List;
import java.util.Optional;

import com.knnsystem.api.dto.UsuarioCadastroDTO;
import com.knnsystem.api.dto.UsuarioResumoDTO;
import com.knnsystem.api.model.entity.Usuario;

public interface UsuarioService {

	UsuarioResumoDTO salvar (UsuarioCadastroDTO dto);

}
