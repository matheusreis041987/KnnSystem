package com.knnsystem.api.controller;

import com.knnsystem.api.dto.MoradorDTO;
import com.knnsystem.api.exceptions.EntidadeNaoEncontradaException;
import com.knnsystem.api.service.MoradorService;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

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

    @GetMapping("/consulta")
    public ResponseEntity<List<MoradorDTO>> listar(
            @RequestParam(value = "cpf", required = false) @CPF String cpf,
            @RequestParam(value = "nome", required = false) String nome
    ){
        List<MoradorDTO> moradores = service.listar(cpf, nome);
        if (moradores.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Erro - Morador não cadastrado");
        }
        return ResponseEntity.ok(moradores);
    }
}
