package com.knnsystem.api.service.impl;

import com.knnsystem.api.dto.FornecedorDTO;
import com.knnsystem.api.exceptions.EntidadeCadastradaException;
import com.knnsystem.api.exceptions.EntidadeNaoEncontradaException;
import com.knnsystem.api.exceptions.RegraNegocioException;
import com.knnsystem.api.exceptions.RelatorioSemResultadoException;
import com.knnsystem.api.infrastructure.api.documental.ApiDocumentoFacade;
import com.knnsystem.api.model.entity.DomicilioBancario;
import com.knnsystem.api.model.entity.Fornecedor;
import com.knnsystem.api.model.entity.StatusGeral;
import com.knnsystem.api.model.repository.DomicilioBancarioRepository;
import com.knnsystem.api.model.repository.FornecedorRepository;
import com.knnsystem.api.model.repository.ResponsavelRepository;
import com.knnsystem.api.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Transactional
    public FornecedorDTO salvar(FornecedorDTO dto) {
        if (fornecedorRepository.findByCnpj(dto.cnpj()).isPresent()){
            throw new EntidadeCadastradaException("Já há um fornecedor cadastrado para os dados informados");
        }

        boolean isCnpjValido =  apiDocumentoFacade.validarDocumento(dto.cnpj());

        if (!isCnpjValido) {
            throw new RegraNegocioException("CNPJ inválido");
        }

        var fornecedor = dto.toModel(true);


        // salva o responsável
        var responsavelSalvo = responsavelRepository.save(fornecedor.getResponsavel());

        // salva o fornecedor
        var fornecedorSalvo = fornecedorRepository.save(fornecedor);

        // salva o domicílio bancário após
        var domicilioASalvar = dto.domicilioBancario().toModel(true);
        domicilioASalvar.setFornecedor(fornecedorSalvo);
        domicilioBancarioRepository.save(domicilioASalvar);

        return new FornecedorDTO(fornecedorSalvo);
    }

    @Override
    @Transactional
    public List<FornecedorDTO> listar(String cnpj, String razaoSocial, String numeroControle) {
        List<Fornecedor> fornecedores;
        if (cnpj == null && razaoSocial == null && numeroControle == null) {
            fornecedores = fornecedorRepository.findAll();
        } else  {
            fornecedores = fornecedorRepository
                    .findByCnpjOrRazaoSocialOrNumControle(cnpj, razaoSocial, numeroControle);
        }

        List<FornecedorDTO> fornecedorDTOS = new ArrayList<>();
        for (Fornecedor fornecedor: fornecedores){
            var domicilioBancarioOptional = domicilioBancarioRepository.findByFornecedor(fornecedor);
            DomicilioBancario domicilioBancario;
            if (domicilioBancarioOptional.isEmpty()){
                domicilioBancario = new DomicilioBancario();
            } else {
                domicilioBancario = domicilioBancarioOptional.get();
            }

            fornecedor.setDomicilioBancario(domicilioBancario);

            fornecedorDTOS.add(new FornecedorDTO(fornecedor));

        }
        return fornecedorDTOS;
    }

    @Override
    @Transactional
    public void inativar(Long id) {
        var fornecedor = fornecedorRepository.findById(id);
        fornecedor
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Não existe fornecedor para os dados pesquisados"))
                .setStatusFornecedor(StatusGeral.INATIVO);
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        var entidadeAExcluir = fornecedorRepository.findById(id);
        if (entidadeAExcluir.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Não existe o registro solicitado");
        }
        entidadeAExcluir.ifPresent(fornecedorRepository::delete);
    }

    @Override
    public List<FornecedorDTO> listarAtivos() {
        List<Fornecedor> fornecedores = fornecedorRepository
                .findAll()
                .stream()
                .filter(fornecedor -> fornecedor.getStatusFornecedor().equals(StatusGeral.ATIVO))
                .toList();

        if (fornecedores.isEmpty()) {
            throw new RelatorioSemResultadoException("Erro -  não há dados para o relatório");
        }

        List<FornecedorDTO> fornecedorDTOS = new ArrayList<>();
        for (Fornecedor fornecedor: fornecedores){
            var domicilioBancarioOptional = domicilioBancarioRepository.findByFornecedor(fornecedor);
            DomicilioBancario domicilioBancario;
            if (domicilioBancarioOptional.isEmpty()){
                domicilioBancario = new DomicilioBancario();
            } else {
                domicilioBancario = domicilioBancarioOptional.get();
            }

            fornecedor.setDomicilioBancario(domicilioBancario);

            fornecedorDTOS.add(new FornecedorDTO(fornecedor));

        }

        return fornecedorDTOS;
    }
}
