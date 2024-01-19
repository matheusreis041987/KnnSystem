package com.knnsystem.api.controller;

import com.knnsystem.api.dto.MoradorDTO;
import com.knnsystem.api.service.MoradorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/morador/api")
public class MoradorController {

    @Autowired
    private MoradorService service;

    @PostMapping("/cadastra")
    public ResponseEntity<MoradorDTO> cadastra(
            @RequestBody @Valid MoradorDTO dto,
            UriComponentsBuilder uriComponentsBuilder
    ){
        var moradorSalvo = service.salvar(dto);
        var uri = uriComponentsBuilder.path("/morador/api/cadastra/{id}").buildAndExpand(moradorSalvo.id()).toUri();
        return ResponseEntity.created(uri).body(moradorSalvo);
    }
}
