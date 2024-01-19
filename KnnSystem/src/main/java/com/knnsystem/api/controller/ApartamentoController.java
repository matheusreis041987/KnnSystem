package com.knnsystem.api.controller;

import com.knnsystem.api.dto.ApartamentoFormularioDTO;
import com.knnsystem.api.service.ApartamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/apartamento/api")
@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
public class ApartamentoController {

    @Autowired
    private ApartamentoService apartamentoService;

    @PostMapping("/cadastra")
    public ResponseEntity<ApartamentoFormularioDTO> cadastra(
            @RequestBody @Valid ApartamentoFormularioDTO dto,
            UriComponentsBuilder uriComponentsBuilder
            ) {
        var apartamentoSalvo = apartamentoService.salvar(dto);
        var uri = uriComponentsBuilder.path("/apartamento/api/cadastra/{id}").buildAndExpand(apartamentoSalvo.id()).toUri();
        return ResponseEntity.created(uri).body(apartamentoSalvo);
    }

}
