package com.knnsystem.api.controller;


import com.knnsystem.api.dto.FornecedorDTO;
import com.knnsystem.api.service.FornecedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

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


}
