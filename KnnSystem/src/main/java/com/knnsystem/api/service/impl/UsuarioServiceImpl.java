package com.knnsystem.api.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.knnsystem.api.dto.UsuarioCadastroDTO;
import com.knnsystem.api.exceptions.UsuarioCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.exceptions.ErroAutenticacao;
import com.knnsystem.api.exceptions.RegraNegocioException;
import com.knnsystem.api.model.entity.Usuario;
import com.knnsystem.api.model.repository.UsuarioRepository;
import com.knnsystem.api.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Override
	@Transactional
	public UsuarioCadastroDTO salvar(UsuarioCadastroDTO dto) {

		if (repository.findByCpf(dto.cpf()).isPresent()) {
			throw new UsuarioCadastradoException("CPF já cadastrado");
		}


		return null;
	}

}


