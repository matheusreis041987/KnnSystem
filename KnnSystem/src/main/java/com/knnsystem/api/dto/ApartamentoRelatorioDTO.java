package com.knnsystem.api.dto;

import com.knnsystem.api.model.entity.Apartamento;

public record ApartamentoRelatorioDTO (
		int numeroDoApartamento,
		String bloco,
		String nomeDoProprietario,
		String nomeDoMorador,
		String telefoneDoProprietario,
		String telefoneDoMorador,
		String cpfDoProprietario,
		String cpfDoMorador,
		String emailDoProprietario,
		String emailDoMorador,
		int metragemDoImovel) {

	public ApartamentoRelatorioDTO(Apartamento apartamento){
		this(apartamento.getNumApt(), apartamento.getBlocoApt(),
				apartamento.getProprietario().getNome(),
				apartamento.getMorador().getNome(),
				apartamento.getProprietario().getTelefones().stream().findFirst().toString(),
				apartamento.getMorador().getTelefones().stream().findFirst().toString(),
				apartamento.getProprietario().getCpf(),
				apartamento.getMorador().getCpf(),
				apartamento.getProprietario().getEmail(),
				apartamento.getMorador().getEmail(),
				apartamento.getMetragem());

	}
}
