package com.knnsystem.api.controller;

import com.knnsystem.api.dto.FaturaCadastroDTO;
import com.knnsystem.api.dto.ResultadoPagamentoDTO;
import com.knnsystem.api.service.FaturaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
