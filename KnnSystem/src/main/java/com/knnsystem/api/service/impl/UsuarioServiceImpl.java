package com.knnsystem.api.service.impl;

import java.util.Optional;

import com.knnsystem.api.dto.UsuarioCadastroDTO;
import com.knnsystem.api.dto.UsuarioConsultaDTO;
import com.knnsystem.api.dto.UsuarioResumoDTO;
import com.knnsystem.api.exceptions.UsuarioCadastradoException;
import com.knnsystem.api.exceptions.UsuarioNaoEncontradoException;
import com.knnsystem.api.model.entity.Cargo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knnsystem.api.model.repository.UsuarioRepository;
import com.knnsystem.api.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public UsuarioResumoDTO salvar(UsuarioCadastroDTO dto) {

		if (repository.findByCpf(dto.cpf()).isPresent()) {
			throw new UsuarioCadastradoException("CPF já cadastrado");
		}

		var usuario = dto.toModel(passwordEncoder);

		var usuarioSalvo = repository.save(usuario);

		return new UsuarioResumoDTO(usuarioSalvo);
	}

	@Override
	@Transactional
	public UsuarioResumoDTO editar(String cpf, UsuarioCadastroDTO dto) {
		if (repository.findByCpf(dto.cpf()).isEmpty()) {
			throw new UsuarioNaoEncontradoException("CPF inválido");
		}
		var usuarioAEditar = dto.toModel(passwordEncoder);
		usuarioAEditar.setNome(dto.nome());
		usuarioAEditar.setCpf(dto.cpf());
		usuarioAEditar.setEmail(dto.email());
		usuarioAEditar.setDataNascimento(dto.dataNascimento());
		usuarioAEditar.setCargo(Cargo.valueOf(dto.cargo()));

		return null;
	}

	@Override
	@Transactional
	public Optional<UsuarioConsultaDTO> consultarPorCPF(String cpf) {


		if (cpf == null){
			throw new UsuarioNaoEncontradoException("Erro - CPF é um campo obrigatório");
		}

		var usuario = repository.findByCpf(cpf);

		if (usuario.isEmpty()){
			throw new UsuarioNaoEncontradoException("Erro - não há usuário cadastrado para esse CPF");
		}

		return Optional.of(new UsuarioConsultaDTO(usuario.get()));
	}

}


