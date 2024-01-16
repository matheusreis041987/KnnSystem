package com.knnsystem.api.controller;

import com.knnsystem.api.dto.UsuarioCadastroDTO;
import com.knnsystem.api.exceptions.UsuarioCadastradoException;
import com.knnsystem.api.model.repository.UsuarioRepository;
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
    private UsuarioRepository repository;

    @PostMapping("/cadastra")
    public ResponseEntity<String> cadastra(
            @RequestBody @Valid UsuarioCadastroDTO dto
    ){
        if (repository.findByCpf(dto.cpf()).isPresent()) {
            throw new UsuarioCadastradoException("CPF já cadastrado");
        }
        return ResponseEntity.ok().build();
    }

}
