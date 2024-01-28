package com.knnsystem.api.controller;


import com.knnsystem.api.dto.ApartamentoFormularioDTO;
import com.knnsystem.api.dto.ContratoDTO;
import com.knnsystem.api.service.ApartamentoService;
import com.knnsystem.api.service.ContratoService;
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

    @Autowired
    private ContratoService contratoService;

    @GetMapping("/apartamentos")
    public ResponseEntity<List<ApartamentoFormularioDTO>> listaApartamentos() {
        var relatorio = apartamentoService.listar();
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/contratos-vigentes")
    public ResponseEntity<List<ContratoDTO>> listarContratosVigentes(){
        var relatorio = contratoService.listarVigentes();
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/contratos-vencidos")
    public ResponseEntity<List<ContratoDTO>> listarContratosVencidos(){
        var relatorio = contratoService.listarVencidos();
        return ResponseEntity.ok(relatorio);
    }


}
