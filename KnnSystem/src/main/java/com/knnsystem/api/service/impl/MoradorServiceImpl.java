package com.knnsystem.api.service.impl;

import com.knnsystem.api.dto.MoradorDTO;
import com.knnsystem.api.exceptions.EntidadeCadastradaException;
import com.knnsystem.api.exceptions.EntidadeNaoEncontradaException;
import com.knnsystem.api.model.entity.Telefone;
import com.knnsystem.api.model.repository.PessoaRepository;
import com.knnsystem.api.model.repository.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.knnsystem.api.model.repository.MoradorRepository;
import com.knnsystem.api.service.MoradorService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MoradorServiceImpl implements MoradorService {
	@Autowired
	private MoradorRepository repository;

	@Autowired
	private TelefoneRepository telefoneRepository;
	@Autowired
	private PessoaRepository pessoaRepository;

	@Override
	@Transactional
	public MoradorDTO salvar(MoradorDTO moradorDTO) {
		if (repository.findByCpf(moradorDTO.cpf()).isPresent()) {
			throw new EntidadeCadastradaException("Já há um morador cadastrado para os dados informados");
		}

		var morador = moradorDTO.toModel(true);

		var moradorSalvo = repository.save(morador);

		var telefoneOptional = moradorSalvo.getTelefones().stream().findFirst();
		if (telefoneOptional.isPresent()) {
			var telefone = telefoneOptional.get();
			var pessoaMorador = pessoaRepository.getReferenceById(morador.getId());
			telefone.setPessoa(pessoaMorador);
			telefoneRepository.save(telefone);
		}


		return new MoradorDTO(moradorSalvo);

	}

	@Override
	@Transactional
	public List<MoradorDTO> listar(String cpf, String nome) {
		return repository
				.findByCpfOrNome(cpf, nome)
				.stream()
				.map(MoradorDTO::new)
				.toList();
	}

	@Override
	public MoradorDTO atualizar(Long id, MoradorDTO dto) {
		var moradorOptional = repository.findById(id);
		if (moradorOptional.isEmpty()) {
			throw new EntidadeNaoEncontradaException("Erro - Morador não cadastrado");
		}
		var morador = moradorOptional.get();
		morador.setCpf(dto.cpf());
		morador.setNome(dto.nome());
		morador.setEmail(dto.email());
		Telefone telefone = new Telefone();
		telefone.setNumero(dto.telefone());
		morador.adicionaTelefone(telefone);
		morador.setNumApt(dto.numeroDoApartamento());
		morador.setBloco(dto.blocoDoApartamento());
		return new MoradorDTO(morador);
	}
}
