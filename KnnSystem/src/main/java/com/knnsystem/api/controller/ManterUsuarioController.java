package com.knnsystem.api.controller;

import com.knnsystem.api.dto.UsuarioCadastroDTO;
import com.knnsystem.api.dto.UsuarioConsultaDTO;
import com.knnsystem.api.dto.UsuarioResumoDTO;
import com.knnsystem.api.exceptions.UsuarioNaoEncontradoException;
import com.knnsystem.api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuario/api")
public class ManterUsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/cadastra")
    public ResponseEntity<UsuarioResumoDTO> cadastra(
            @RequestBody @Valid UsuarioCadastroDTO dto,
            UriComponentsBuilder uriComponentsBuilder
    ){
        var usarioSalvo = service.salvar(dto);
        var uri = uriComponentsBuilder.path("/usuario/api/cadastra/{id}").buildAndExpand(usarioSalvo.id()).toUri();
        return ResponseEntity.created(uri).body(usarioSalvo);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<UsuarioConsultaDTO> consulta(
            @PathVariable String cpf
    ){
        var dto = service.consultarPorCPF(cpf);

        if (dto.isEmpty()){
            throw new UsuarioNaoEncontradoException("Erro - não há usuário cadastrado para esse CPF");
        }

        return ResponseEntity.ok(dto.get());
    }

}
