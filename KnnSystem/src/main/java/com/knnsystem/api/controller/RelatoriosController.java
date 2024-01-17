package com.knnsystem.api.controller;


import com.knnsystem.api.dto.ApartamentoRelatorioDTO;
import com.knnsystem.api.service.ApartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/relatorio/api")
@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_SINDICO')")
public class RelatoriosController {

    @Autowired
    private ApartamentoService apartamentoService;

    @GetMapping("/apartamentos")
    public ResponseEntity<List<ApartamentoRelatorioDTO>> listaApartamentos() {
        var relatorio = apartamentoService.listar();
        return ResponseEntity.ok(relatorio);
    }


}
