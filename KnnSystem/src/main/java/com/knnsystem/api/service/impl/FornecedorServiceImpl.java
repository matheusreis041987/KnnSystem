package com.knnsystem.api.service.impl;

import com.knnsystem.api.dto.FornecedorDTO;
import com.knnsystem.api.exceptions.EntidadeCadastradaException;
import com.knnsystem.api.model.repository.DomicilioBancarioRepository;
import com.knnsystem.api.model.repository.FornecedorRepository;
import com.knnsystem.api.model.repository.ResponsavelRepository;
import com.knnsystem.api.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FornecedorServiceImpl implements FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private ResponsavelRepository responsavelRepository;

    @Autowired
    private DomicilioBancarioRepository domicilioBancarioRepository;

    @Override
    public FornecedorDTO salvar(FornecedorDTO dto) {
        if (fornecedorRepository.findByCnpj(dto.cnpj()).isPresent()){
            throw new EntidadeCadastradaException("Já há um fornecedor cadastrado para os dados informados");
        }
        var fornecedor = dto.toModel(true);
        // salva entidades relacionadas primeiro
        responsavelRepository.save(fornecedor.getResponsavel());
        domicilioBancarioRepository.save(fornecedor.getDomicilioBancario());

        // salva o fornecedor
        var fornecedorSalvo = fornecedorRepository.save(fornecedor);

        return new FornecedorDTO(fornecedorSalvo);
    }
}
