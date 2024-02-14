package com.knnsystem.api.service.impl;

import com.knnsystem.api.dto.UsuarioCadastroDTO;
import com.knnsystem.api.dto.UsuarioConsultaDTO;
import com.knnsystem.api.dto.UsuarioResumoDTO;
import com.knnsystem.api.exceptions.EntidadeCadastradaException;
import com.knnsystem.api.exceptions.EntidadeNaoEncontradaException;
import com.knnsystem.api.model.entity.Cargo;
import com.knnsystem.api.model.entity.Sindico;
import com.knnsystem.api.model.entity.StatusGeral;
import com.knnsystem.api.model.entity.Usuario;
import com.knnsystem.api.model.repository.SindicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

	@Autowired
	private SindicoRepository sindicoRepository;

	@Override
	@Transactional
	public UsuarioResumoDTO salvar(UsuarioCadastroDTO dto) {

		if (repository.findByCpf(dto.cpf()).isPresent()) {
			throw new EntidadeCadastradaException("CPF já cadastrado");
		}

		var usuario = dto.toModel(passwordEncoder);

		if (usuario.getCargo().equals(Cargo.SINDICO)) {
			var sindico = new Sindico();
			sindico.setEmail(usuario.getEmail());
			sindico.setCpf(usuario.getCpf());
			sindico.setNome(usuario.getNome());

			sindicoRepository.save(sindico);
		}

		var usuarioSalvo = repository.save(usuario);

		return new UsuarioResumoDTO(usuarioSalvo);
	}

	@Override
	@Transactional
	public UsuarioResumoDTO editar(String cpf, UsuarioCadastroDTO dto) {
		if (repository.findByCpf(dto.cpf()).isEmpty()) {
			throw new EntidadeNaoEncontradaException("CPF inválido");
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
	public UsuarioConsultaDTO consultarPorCPF(String cpf) {
		return new UsuarioConsultaDTO(consultarUsuarioPorCPF(cpf));
	}

	@Override
	@Transactional
	public UsuarioConsultaDTO ativar(String cpf) {
		var usuario = consultarUsuarioPorCPF(cpf);
		usuario.getPessoa().setStatus(StatusGeral.ATIVO);
		return new UsuarioConsultaDTO(usuario);
	}

	@Override
	@Transactional
	public UsuarioConsultaDTO inativar(String cpf) {
		var usuario = consultarUsuarioPorCPF(cpf);
		usuario.getPessoa().setStatus(StatusGeral.INATIVO);
		return new UsuarioConsultaDTO(usuario);

	}

	private Usuario consultarUsuarioPorCPF(String cpf){
		if (cpf == null){
			throw new EntidadeNaoEncontradaException("Erro - CPF é um campo obrigatório");
		}

		var usuario = repository.findByCpf(cpf);

		if (usuario.isEmpty()) {
			throw new EntidadeNaoEncontradaException("Erro - não há usuário cadastrado para esse CPF");
		}

		return usuario.get();
	}
	@Override
	public Usuario getUsuarioLogado(){
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	@Override
	@Transactional
	public void excluir(Long id) {
		var entidadeAExcluir = repository.findById(id);
		if (entidadeAExcluir.isEmpty()) {
			throw new EntidadeNaoEncontradaException("Não existe o registro solicitado");
		}
		entidadeAExcluir.ifPresent(repository::delete);
	}
}


