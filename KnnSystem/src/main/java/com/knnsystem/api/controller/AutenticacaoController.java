package com.knnsystem.api.controller;

import com.knnsystem.api.dto.AutenticacaoDTO;
import com.knnsystem.api.dto.TokenLoginDTO;
import com.knnsystem.api.infrastructure.security.TokenService;
import com.knnsystem.api.model.entity.Usuario;
import com.knnsystem.api.model.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/api")
public class AutenticacaoController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity<TokenLoginDTO> autentica(
            @RequestBody @Valid AutenticacaoDTO dto
            ){
        var usuarioSenha = new UsernamePasswordAuthenticationToken(
                dto.cpf(),
                dto.senha());

        var auth = authenticationManager.authenticate(usuarioSenha);

        var token = tokenService.geraToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new TokenLoginDTO(token));

    }


}
