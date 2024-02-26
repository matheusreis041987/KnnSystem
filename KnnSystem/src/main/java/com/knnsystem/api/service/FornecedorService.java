package com.knnsystem.api.service;

import com.knnsystem.api.dto.FornecedorDTO;

import java.util.List;

public interface FornecedorService {
    FornecedorDTO salvar(FornecedorDTO dto);

    List<FornecedorDTO> listar(String cnpj, String razaoSocial, String numeroControle);

    void inativar(Long id);

    void excluir(Long id);

    List<FornecedorDTO> listarAtivos();

    FornecedorDTO atualizar(Long id, FornecedorDTO dto);

    void ativar(Long id);
}
