package com.knnsystem.api.controller;

import com.knnsystem.api.dto.AutenticacaoDTO;
import com.knnsystem.api.dto.AutenticacaoEsqueciSenhaDTO;
import com.knnsystem.api.dto.AutenticacaoProvisoriaDTO;
import com.knnsystem.api.dto.TokenLoginDTO;
import com.knnsystem.api.exceptions.ErroAutenticacao;
import com.knnsystem.api.infrastructure.security.TokenService;
import com.knnsystem.api.model.entity.Usuario;
import com.knnsystem.api.model.repository.UsuarioRepository;
import com.knnsystem.api.service.EsqueciSenhaEmailService;
import com.knnsystem.api.service.EsqueciSenhaGerarSenhaService;
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

    @Autowired
    private EsqueciSenhaEmailService esqueciSenhaEmailService;

    @Autowired
    private EsqueciSenhaGerarSenhaService esqueciSenhaGerarSenhaService;


    @PostMapping("/login")
    public ResponseEntity<TokenLoginDTO> autentica(
            @RequestBody @Valid AutenticacaoDTO dto
            ){
        var token = getToken(dto.cpf(), dto.senha());

        return ResponseEntity.ok(new TokenLoginDTO(token));

    }

    @PostMapping("/redefine")
    public ResponseEntity<TokenLoginDTO> redefine(
            @RequestBody @Valid AutenticacaoProvisoriaDTO dto
    ){
        var token = getToken(dto.cpf(), dto.senhaProvisoria());

        if (token != null) {
            var usuario = repository.findByCpf(dto.cpf());

            if (usuario.isPresent()){
                usuario.get().setSenha(passwordEncoder.encode(dto.novaSenha()));
                repository.save(usuario.get());
            }

        }

        return ResponseEntity.ok(new TokenLoginDTO(token));

    }

    @PostMapping("/esqueci-senha")
    public ResponseEntity geraSenhaProvisoria(
            @RequestBody @Valid AutenticacaoEsqueciSenhaDTO dto
    ) {
        var usuarioOptional = repository.findByCpf(dto.cpf());

        if (usuarioOptional.isPresent()) {
            // Gera nova senha, envia link por e-mail, salva no banco a senha provisória
            var usuario = usuarioOptional.get();
            var senhaProvisoria = esqueciSenhaGerarSenhaService.gerarSenhaProvisoria();
            esqueciSenhaEmailService.enviarLinkSenhaProvisoria(
                    usuario.getEmail(),
                    senhaProvisoria);
            usuario.setSenha(senhaProvisoria);
        }

        return ResponseEntity.ok().build();
    }

    private String getToken(String cpf, String senha) {

        var usuarioOptional = repository
                .findByCpf(cpf);

        if (usuarioOptional.isEmpty() || !usuarioOptional.get().isAtivo()) {
            throw new ErroAutenticacao("Não há usuário ativo para o CPF informado");
        }

        var usuarioSenha = new UsernamePasswordAuthenticationToken(
                cpf,
                senha);

        var auth = authenticationManager.authenticate(usuarioSenha);

        return tokenService.geraToken((Usuario) auth.getPrincipal());
    }


}
