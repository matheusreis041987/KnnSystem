package com.knnsystem.api.controller;

import com.knnsystem.api.dto.UsuarioCadastroDTO;
import com.knnsystem.api.dto.UsuarioConsultaDTO;
import com.knnsystem.api.dto.UsuarioResumoDTO;
import com.knnsystem.api.exceptions.UsuarioNaoEncontradoException;
import com.knnsystem.api.service.UsuarioService;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuario/api")
@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
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
            @PathVariable @CPF String cpf
    ){
        var dto = service.consultarPorCPF(cpf);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<String> altera(
            @PathVariable @CPF String cpf,
            @RequestBody @Valid UsuarioCadastroDTO dto
    ){
        service.editar(cpf, dto);
        return ResponseEntity.ok("usuário foi atualizado");
    }

    @PutMapping("/ativa/{cpf}")
    public ResponseEntity<UsuarioConsultaDTO> ativa(
            @PathVariable @CPF String cpf
    ) {
        var dto = service.ativar(cpf);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/inativa/{cpf}")
    public ResponseEntity<UsuarioConsultaDTO> inativa(
            @PathVariable @CPF String cpf
    ) {
        var dto = service.inativar(cpf);
        return ResponseEntity.ok(dto);
    }

}
