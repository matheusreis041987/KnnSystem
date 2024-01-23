package com.knnsystem.api.service.impl;

import com.knnsystem.api.dto.FornecedorDTO;
import com.knnsystem.api.exceptions.EntidadeCadastradaException;
import com.knnsystem.api.exceptions.RegraNegocioException;
import com.knnsystem.api.infrastructure.api.documental.ApiDocumentoFacade;
import com.knnsystem.api.model.repository.DomicilioBancarioRepository;
import com.knnsystem.api.model.repository.FornecedorRepository;
import com.knnsystem.api.model.repository.ResponsavelRepository;
import com.knnsystem.api.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FornecedorServiceImpl implements FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private ResponsavelRepository responsavelRepository;

    @Autowired
    private DomicilioBancarioRepository domicilioBancarioRepository;

    @Autowired
    private ApiDocumentoFacade apiDocumentoFacade;

    @Override
    public FornecedorDTO salvar(FornecedorDTO dto) {
        if (fornecedorRepository.findByCnpj(dto.cnpj()).isPresent()){
            throw new EntidadeCadastradaException("Já há um fornecedor cadastrado para os dados informados");
        }

        boolean isCnpjValido =  apiDocumentoFacade.validarDocumento(dto.cnpj());

        if (!isCnpjValido) {
            throw new RegraNegocioException("CNPJ inválido");
        }

        var fornecedor = dto.toModel(true);


        // salva entidades relacionadas primeiro
        responsavelRepository.save(fornecedor.getResponsavel());
        domicilioBancarioRepository.save(fornecedor.getDomicilioBancario());

        // salva o fornecedor
        var fornecedorSalvo = fornecedorRepository.save(fornecedor);

        return new FornecedorDTO(fornecedorSalvo);
    }

    @Override
    public List<FornecedorDTO> listar(String cnpj, String razaoSocial, Long numeroControle) {
        return fornecedorRepository
                .findByCnpjOrRazaoSocialOrNumControle(cnpj, razaoSocial, numeroControle)
                .stream()
                .map(FornecedorDTO::new)
                .toList();
    }
}
