package com.knnsystem.api.controller;

import com.knnsystem.api.dto.ContratoDTO;
import com.knnsystem.api.dto.ReajusteParametrosDTO;
import com.knnsystem.api.dto.RescisaoCadastroDTO;
import com.knnsystem.api.exceptions.EntidadeNaoEncontradaException;
import com.knnsystem.api.service.ContratoService;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/contrato/api")
public class ContratoController {

    @Autowired
    private ContratoService service;

    @PostMapping("/cadastra")
    public ResponseEntity<ContratoDTO> cadastra(
            @RequestBody @Valid ContratoDTO dto,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        var contratoSalvo = service.salvar(dto);
        var uri = uriComponentsBuilder.path("/fornecedor/api/cadastra/{id}").buildAndExpand(contratoSalvo.id()).toUri();
        return ResponseEntity.created(uri).body(contratoSalvo);
    }

    @GetMapping("/consulta")
    public ResponseEntity<List<ContratoDTO>> listar(
            @RequestParam(value = "cnpjFornecedor", required = false) @CNPJ String cnpjFornecedor,
            @RequestParam(value = "razaoSocial", required = false) String razaoSocial,
            @RequestParam(value = "numeroControle", required = false) String numeroControle
    ){
        List<ContratoDTO> contratos = service.listar(cnpjFornecedor, razaoSocial, numeroControle);
        if (contratos.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Não há um contrato cadastrado para os dados informados");
        }
        return ResponseEntity.ok(contratos);

    }

    @PutMapping("/inativa/{id}")
    public ResponseEntity inativar(
            @PathVariable Long id
    ) {
        service.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/reajusta/{id}")
    public ResponseEntity reajustar(
            @PathVariable Long id,
            @RequestBody @Valid ReajusteParametrosDTO dto
            ) {
        service.reajustar(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/rescinde/{id}")
    public ResponseEntity rescindir(
            @PathVariable Long id,
            @RequestBody @Valid RescisaoCadastroDTO dto
    ) {
        service.rescindir(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/exclui/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public ResponseEntity excluir(@PathVariable Long id){
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/atualiza/{id}")
    public ResponseEntity atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ContratoDTO dto
    ){
        var dtoAtualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(dtoAtualizado);
    }

}
