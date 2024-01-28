package com.knnsystem.api.controller;

import com.knnsystem.api.dto.ApartamentoFormularioDTO;
import com.knnsystem.api.exceptions.EntidadeNaoEncontradaException;
import com.knnsystem.api.service.ApartamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

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

    @GetMapping("/consulta")
    public ResponseEntity<List<ApartamentoFormularioDTO>> listar(
            @RequestParam(value = "numero", required = false) Integer numero,
            @RequestParam(value = "bloco", required = false) String bloco
    ){
        List<ApartamentoFormularioDTO> apartamentos = apartamentoService.listar(numero, bloco);
        if (apartamentos.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Não há um apartamento cadastrado para os dados informados");
        }
        return ResponseEntity.ok(apartamentos);
    }

    @PutMapping("/inativa")
    public ResponseEntity<ApartamentoFormularioDTO> inativar(
            @RequestParam(value = "numero", required = false) Integer numero,
            @RequestParam(value = "bloco", required = false) String bloco
    ){
        ApartamentoFormularioDTO dto = apartamentoService.inativar(numero, bloco);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/exclui/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public ResponseEntity excluir(@PathVariable Long id){
        apartamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
