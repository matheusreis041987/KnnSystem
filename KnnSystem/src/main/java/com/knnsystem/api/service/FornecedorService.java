package com.knnsystem.api.service;

import com.knnsystem.api.dto.FornecedorDTO;

import java.util.List;

public interface FornecedorService {
    FornecedorDTO salvar(FornecedorDTO dto);

    List<FornecedorDTO> listar(String cnpj, String razaoSocial, Long numeroControle);
}
