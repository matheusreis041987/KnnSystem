package com.knnsystem.api.service.impl;

import com.knnsystem.api.service.EsqueciSenhaGerarSenhaService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EsqueciSenhaGerarSenhaServiceImpl implements EsqueciSenhaGerarSenhaService {
    @Override
    public String gerarSenhaProvisoria() {
        var senhaProvisoria = UUID.randomUUID().toString();
        System.out.println(senhaProvisoria);
        return senhaProvisoria;
    }
}
