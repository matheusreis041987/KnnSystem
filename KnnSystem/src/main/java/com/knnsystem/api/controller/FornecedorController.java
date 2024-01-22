package com.knnsystem.api.controller;


import com.knnsystem.api.dto.FornecedorDTO;
import com.knnsystem.api.exceptions.EntidadeNaoEncontradaException;
import com.knnsystem.api.service.FornecedorService;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/fornecedor/api")
public class FornecedorController {

    @Autowired
    private FornecedorService service;

    @PostMapping("/cadastra")
    public ResponseEntity<FornecedorDTO> cadastra(
            @RequestBody @Valid FornecedorDTO dto,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        var fornecedorSalvo = service.salvar(dto);
        var uri = uriComponentsBuilder.path("/fornecedor/api/cadastra/{id}").buildAndExpand(fornecedorSalvo.id()).toUri();
        return ResponseEntity.created(uri).body(fornecedorSalvo);
    }

    @GetMapping("/consulta")
    public ResponseEntity<List<FornecedorDTO>> listar(
            @RequestParam(value = "cnpj", required = false) @CNPJ String cnpj,
            @RequestParam(value = "razaoSocial", required = false) String razaoSocial,
            @RequestParam(value = "numeroControle", required = false) Long numeroControle
    ){
        List<FornecedorDTO> fornecedores = service.listar(cnpj, razaoSocial, numeroControle);
        if (fornecedores.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Não existe fornecedor para os dados pesquisados");
        }
        return ResponseEntity.ok(fornecedores);

    }


}
