package com.knnsystem.api.controller;

import com.knnsystem.api.dto.ContratoCadastroDTO;
import com.knnsystem.api.service.ContratoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/contrato/api")
public class ContratoController {

    @Autowired
    private ContratoService service;

    @PostMapping("/cadastra")
    public ResponseEntity<ContratoCadastroDTO> cadastra(
            @RequestBody @Valid ContratoCadastroDTO dto,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        var contratoSalvo = service.salvar(dto);
        var uri = uriComponentsBuilder.path("/fornecedor/api/cadastra/{id}").buildAndExpand(contratoSalvo.id()).toUri();
        return ResponseEntity.created(uri).body(contratoSalvo);
    }
}
