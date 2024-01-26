package com.knnsystem.api.controller;

import com.knnsystem.api.dto.FaturaCadastroDTO;
import com.knnsystem.api.dto.FaturaResultadoDTO;
import com.knnsystem.api.dto.ResultadoPagamentoDTO;
import com.knnsystem.api.exceptions.EntidadeNaoEncontradaException;
import com.knnsystem.api.service.FaturaService;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fatura/api")
public class FaturaController {

    @Autowired
    private FaturaService service;

    @PostMapping("/cadastra")
    public ResponseEntity<ResultadoPagamentoDTO> cadastrar(
            @RequestBody @Valid FaturaCadastroDTO dto
            ){
        var resultadoPagamento = service.salvar(dto);
        return ResponseEntity.accepted().body(resultadoPagamento);
    }

    @GetMapping("/consulta")
    public ResponseEntity<List<FaturaResultadoDTO>> listar(
            @RequestParam(value = "cnpjFornecedor", required = false) @CNPJ String cnpjFornecedor,
            @RequestParam(value = "razaoSocial", required = false) String razaoSocial,
            @RequestParam(value = "numeroContrato", required = false) String numeroContrato,
            @RequestParam(value = "numeroFatura", required = false) Long numeroFatura
    ){
        List<FaturaResultadoDTO> faturas = service.listar(cnpjFornecedor, razaoSocial, numeroContrato, numeroFatura);
        if (faturas.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Erro - fatura não encontrada para os dados apresentados");
        }
        return ResponseEntity.ok(faturas);

    }

    @PutMapping("/inativa/{id}")
    public ResponseEntity inativar(
            @PathVariable Long id
    ) {
        service.inativar(id);
        return ResponseEntity.noContent().build();
    }

}
