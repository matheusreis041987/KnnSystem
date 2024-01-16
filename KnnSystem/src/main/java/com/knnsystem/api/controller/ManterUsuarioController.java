package com.knnsystem.api.controller;

import com.knnsystem.api.dto.UsuarioCadastroDTO;
import com.knnsystem.api.exceptions.UsuarioCadastradoException;
import com.knnsystem.api.model.repository.UsuarioRepository;
import com.knnsystem.api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario/api")
public class ManterUsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/cadastra")
    public ResponseEntity<String> cadastra(
            @RequestBody @Valid UsuarioCadastroDTO dto
    ){
        var usarioSalvo = service.salvar(dto);
        return ResponseEntity.ok().build();
    }

}
