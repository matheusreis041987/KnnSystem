package com.knnsystem.api.service;

import java.util.List;
import java.util.Optional;

import com.knnsystem.api.model.entity.Usuario;

public interface UsuarioService {

	Usuario salvar (Usuario UsuarioParm);
	
	Usuario atualizar (Usuario UsuarioParm);
	
	void deletar (Usuario UsuarioParm);
	
	List<Usuario> buscar(Usuario UsuarioParm);

	Optional<Usuario> consultarPorId (Integer idUsuario);
	
	Usuario autentica (String email, String senha);
	
	void validarEmail (String email);
	
	
}
