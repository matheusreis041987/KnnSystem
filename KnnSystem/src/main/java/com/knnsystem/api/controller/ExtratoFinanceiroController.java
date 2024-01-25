package com.knnsystem.api.controller;

import com.knnsystem.api.dto.ExtratoFinanceiroDTO;
import com.knnsystem.api.exceptions.EntidadeNaoEncontradaException;
import com.knnsystem.api.infrastructure.api.financeiro.ApiExtratoFinanceiroService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;

@RestController
@RequestMapping("/extrato/api")
public class ExtratoFinanceiroController {

    @Autowired
    private ApiExtratoFinanceiroService extratoFinanceiroService;

    @GetMapping("/consulta")
    public ResponseEntity<ExtratoFinanceiroDTO> consultar(
            @RequestParam(value = "mesInicio")
            Integer mesInicio,
            @RequestParam(value = "anoInicio")
            Integer anoInicio,
            @RequestParam(value = "mesFim")
            Integer mesFim,
            @RequestParam(value = "anoFim")
            Integer anoFim
    ){
        // converte parâmetros em data
        if (anoInicio < 0) {
            throw new IllegalArgumentException("parâmetros inválidos");
        }
        LocalDate dataInicio = YearMonth.of(anoInicio, mesInicio).atDay(1);
        LocalDate dataFim = YearMonth.of(anoFim, mesFim).atEndOfMonth();

        var extrato = extratoFinanceiroService.gerar(dataInicio, dataFim);

        if (extrato.transacoes().isEmpty()){
            throw new EntidadeNaoEncontradaException("não há valores para o período");
        }

        return ResponseEntity.ok(extrato);
    }
}
